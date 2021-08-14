(ns trustblocks.routes
  (:require [biff.crux :as bcrux]
            [biff.rum :as br]
            [biff.util :as bu]
            [clojure.edn :as edn]
            [clojure.stacktrace :as st]
            [clojure.walk :refer [postwalk]]
            [reitit.ring :as ring]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.coercion.spec :as coercion-spec]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]
            [crux.api :as crux]
            [ring.middleware.anti-forgery :as anti-forgery]
            [trustblocks.routes.auth :as auth]
            [trustblocks.routes.contracts :as contracts]
            [trustblocks.views :as v]
            [trustblocks.views.shared :as shared]))

; See https://biff.findka.com/#http-routes

; Test it out:
; curl http://localhost:8080/echo?foo=bar
; curl -XPOST http://localhost:8080/echo -F foo=bar
; curl -XPOST http://localhost:8080/echo -H 'Content-Type: application/edn' -d '{:foo "bar"}'
; curl -XPOST http://localhost:8080/echo -H 'Content-Type: application/json' -d '{"foo":"bar"}'

(defn echo [req]
  ; Default :status is 200. Default :body is "". :headers/* and
  ; :cookies/* are converted to `:headers {...}` and `:cookies {...}`.
  {:status 200
   :body (merge
           (select-keys req [:params :body-params])
           (bu/select-ns req 'params))})

; This requires authentication, so you'll have to test it from the browser.
(defn whoami [{:keys [biff/uid biff.crux/db] :as sys}]
  {:status 200
   :body (:user/email (crux/entity @db uid))
   :headers/Content-Type "text/plain"})

(defn on-error [{:keys [status uid]}]
  (if (or (= 401 status)
          (and (= 403 status) (not uid)))
    {:status 302
     :headers/Location "/"}
    {:status status
     :headers/Content-Type "text/html"
     :body (str "<h1>" (get bu/http-status->msg status "There was an error.") "</h1>")}))

(defn wrap-signed-in [handler]
  (fn [{:keys [biff/uid] :as req}]
    (if (some? uid)
      (handler req)
      (on-error (assoc req :status 401)))))

(defn ssr [{:keys [biff/uid biff.crux/db params/submitted]}]
  (let [{:user/keys [email foo]} (crux/pull @db [:user/email :user/foo] uid)]
    (v/base
      {}
      [:div
       (shared/header {:email (delay email)})
       [:.h-6]
       (shared/tabs {:active-id (delay :ssr)
                     :tab-data [{:id :crud
                                 :href "/app"
                                 :label "CRUD"}
                                {:id :db
                                 :href "/app/db"
                                 :label "DB Contents"}
                                {:id :ssr
                                 :href "/app/ssr"
                                 :label "SSR"}]})
       [:.h-3]
       [:div "This tab uses server-side rendering instead of React."]
       [:.h-6]
       (br/form
         {:action "/api/form-tx"
          :hidden {"__anti-forgery-token" anti-forgery/*anti-forgery-token*
                   "tx-info"
                   (pr-str
                     {:tx {[:user uid] {:db/update true
                                        :user/foo 'foo}}
                      :fields {'foo :text}
                      :redirect ::ssr
                      :query-params {:submitted true}})}}
         [:.text-lg "Foo: " [:span.font-mono (pr-str foo)]]
         [:.text-sm.text-gray-600
          "This demonstrates submitting a Contract transaction via an HTML form."]
         [:.h-1]
         [:.flex
          [:input.input-text.w-full {:name "foo"
                                     :value foo}]
          [:.w-3]
          [:button.btn {:type "submit"} "Update"]]
         (when submitted
           [:.font-bold.my-3 "Transaction submitted successfully."]))])))

(defn form-tx [req]
  (let [[biff-tx path] (biff.misc/parse-form-tx
                        req
                        {:coercions {:text identity}})]
    (biff.crux/submit-tx (assoc req :biff.crux/authorize true) biff-tx)
    {:status 302
     :headers/location path}))

; See https://cljdoc.org/d/metosin/reitit/0.5.10/doc/introduction#ring-router

;; (defn swagger.json [req]
;; (swagger-ui/create-swagger-ui-handler 
;; {:path "/" 
;; :config {validatorUrl nil
;; :operationSorter "alpha"} } )
;; (ring/create-default-handler))                                    


(defn routes []
  
  [["/api"
    ["/echo" {:get echo
              :post echo}]
    ["/whoami" {:get whoami
                :middleware [wrap-signed-in]}]
    ["/form-tx" {:post form-tx}]]
   ["/app/ssr" {:get #(br/render v/ssr %)
                :middleware [wrap-signed-in]
                :name :ssr
                :biff/redirect true}]
   auth/routes])



(comment  - Jacek Schae - Reitit Course

          (def swagger-docs
            ["/swagger.json"
             {:get
              {:no-doc  true
               :swagger {:basePath "/"
                         :info     {:title       "TrustBlocks API Reference"
                                    :description "The TrustBlocks API is organized around REST. Returns JSON, Transit (msgpack, json), or EDN  encoded responses."
                                    :version     "1.0.0"}}
               :handler (swagger/create-swagger-handler)}}])

          (def router-config
            {:data {:coercion   coercion-spec/coercion
                    :muuntaja   m/instance
                    :middleware [swagger/swagger-feature
                                 muuntaja/format-middleware
                                 exception/exception-middleware
                                 coercion/coerce-request-middleware
                                 coercion/coerce-response-middleware]}})

          (defn routes
            []
            (ring/ring-handler
             (ring/router
              [swagger-docs
               ["/v1"
                (contracts/routes)]]
              router-config)
             (ring/routes
              (swagger-ui/create-swagger-ui-handler {:path "/"})))))



(comment 

  -- Reitit Malli Swagger https://github.com/metosin/reitit/tree/master/examples/ring-malli-swagger

  (def app
  (ring/ring-handler
   (ring/router
    [["/swagger.json"
      {:get {:no-doc true
             :swagger {:info {:title "my-api"
                              :description "with [malli](https://github.com/metosin/malli) and reitit-ring"}
                       :tags [{:name "files", :description "file api"}
                              {:name "math", :description "math api"}]}
             :handler (swagger/create-swagger-handler)}}]

     ["/files"
      {:swagger {:tags ["files"]}}

      ["/upload"
       {:post {:summary "upload a file"
               :parameters {:multipart [:map [:file reitit.ring.malli/temp-file-part]]}
               :responses {200 {:body [:map [:name string?] [:size int?]]}}
               :handler (fn [{{{:keys [file]} :multipart} :parameters}]
                          {:status 200
                           :body {:name (:filename file)
                                  :size (:size file)}})}}]

      ["/download"
       {:get {:summary "downloads a file"
              :swagger {:produces ["image/png"]}
              :handler (fn [_]
                         {:status 200
                          :headers {"Content-Type" "image/png"}
                          :body (-> "reitit.png"
                                    (io/resource)
                                    (io/input-stream))})}}]]

     ["/math"
      {:swagger {:tags ["math"]}}

      ["/plus"
       {:get {:summary "plus with malli query parameters"
              :parameters {:query [:map [:x int?] [:y int?]]}
              :responses {200 {:body [:map [:total int?]]}}
              :handler (fn [{{{:keys [x y]} :query} :parameters}]
                         {:status 200
                          :body {:total (+ x y)}})}
        :post {:summary "plus with malli body parameters"
               :parameters {:body [:map [:x int?] [:y int?]]}
               :responses {200 {:body [:map [:total int?]]}}
               :handler (fn [{{{:keys [x y]} :body} :parameters}]
                          {:status 200
                           :body {:total (+ x y)}})}}]]]

    {;;:reitit.middleware/transform dev/print-request-diffs ;; pretty diffs
       ;;:validate spec/validate ;; enable spec validation for route data
       ;;:reitit.spec/wrap spell/closed ;; strict top-level validation
     :exception pretty/exception
     :data {:coercion (reitit.coercion.malli/create
                       {;; set of keys to include in error messages
                        :error-keys #{#_:type :coercion :in :schema :value :errors :humanized #_:transformed}
                           ;; schema identity function (default: close all map schemas)
                        :compile mu/closed-schema
                           ;; strip-extra-keys (effects only predefined transformers)
                        :strip-extra-keys true
                           ;; add/set default values
                        :default-values true
                           ;; malli options
                        :options nil})
            :muuntaja m/instance
            :middleware [;; swagger feature
                         swagger/swagger-feature
                           ;; query-params & form-params
                         parameters/parameters-middleware
                           ;; content-negotiation
                         muuntaja/format-negotiate-middleware
                           ;; encoding response body
                         muuntaja/format-response-middleware
                           ;; exception handling
                         exception/exception-middleware
                           ;; decoding request body
                         muuntaja/format-request-middleware
                           ;; coercing response bodys
                         coercion/coerce-response-middleware
                           ;; coercing request parameters
                         coercion/coerce-request-middleware
                           ;; multipart
                         multipart/multipart-middleware]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/"
      :config {:validatorUrl nil
               :operationsSorter "alpha"}})
    (ring/create-default-handler))))

(defn start []
  (jetty/run-jetty #'app {:port 3000, :join? false})
  (println "server running in port 3000"))

  )

