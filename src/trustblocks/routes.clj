(ns trustblocks.routes
  (:require [biff.crux :as bcrux]
            [biff.rum :as br]
            [clojure.java.io :as io]
            [biff.util :as bu]
            [clojure.edn :as edn]
            [clojure.stacktrace :as st]
            [clojure.walk :refer [postwalk]]
            [reitit.ring :as ring]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.coercion.spec :as coercion-spec]
            [reitit.coercion.malli :as rcm]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.parameters :as parameters]
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

;; (defn echo [req]
;;   ; Default :status is 200. Default :body is "". :headers/* and
;;   ; :cookies/* are converted to `:headers {...}` and `:cookies {...}`.
;;   {:status 200
;;    :body (merge
;;            (select-keys req [:params :body-params])
;;            (bu/select-ns req 'params))})

;; (def swagger-route
;;   (swagger-ui/create-swagger-ui-handler
;;    {:path "/"
;;     :config {:validatorUrl nil
;;              :operationsSorter "alpha"}}))

;; ; This requires authentication, so you'll have to test it from the browser.
;; (defn whoami [{:keys [biff/uid biff.crux/db] :as sys}]
;;   {:status 200
;;    :body (:user/email (crux/entity @db uid))
;;    :headers/Content-Type "text/plain"})

;; (defn on-error [{:keys [status uid]}]
;;   (if (or (= 401 status)
;;           (and (= 403 status) (not uid)))
;;     {:status 302
;;      :headers/Location "/"}
;;     {:status status
;;      :headers/Content-Type "text/html"
;;      :body (str "<h1>" (get bu/http-status->msg status "There was an error.") "</h1>")}))

;; (defn wrap-signed-in [handler]
;;   (fn [{:keys [biff/uid] :as req}]
;;     (if (some? uid)
;;       (handler req)
;;       (on-error (assoc req :status 401)))))

;; (defn form-tx [req]
;;   (let [[biff-tx path] (biff.misc/parse-form-tx
;;                         req
;;                         {:coercions {:text identity}})]
;;     (biff.crux/submit-tx (assoc req :biff.crux/authorize true) biff-tx)
;;     {:status 302
;;      :headers/location path}))

; See https://cljdoc.org/d/metosin/reitit/0.5.10/doc/introduction#ring-router

;; (defn swagger.json [req]
;; (swagger-ui/create-swagger-ui-handler 
;; {:path "/" 
;; :config {validatorUrl nil
;; :operationSorter "alpha"} } )
;; (ring/create-default-handler))                                    

;; (defn routes []
;;   [["/api"
;;      swagger-route
;;     ["/echo" {:get echo
;;               :post echo}]
;;     ["/whoami" {:get whoami
;;                 :middleware [wrap-signed-in]}]
;;     ["/form-tx" {:post form-tx}]]
;;     ["/app/ssr" {:get #(br/render v/ssr %)
;;                 :middleware [wrap-signed-in]
;;                 :name :ssr
;;                 :biff/redirect true}]
;;      ["swagger.json" {:get 
;;                       {:nodoc true
;;                        :swagger {:info {:title "TrustBlocks-api"
;;                                         :description "Data Driven Law"}}
;;                        :middleware [swagger/swagger-feature]
;;                        :handler (swagger/create-swagger-handler)}}]
;;    auth/routes])


(set! *warn-on-reflection* true)

(defn read-edn-file
  [file-name]
  (try
    (-> file-name io/resource io/as-file slurp read-string)
    (catch Exception _)))

(defn save-contract-data
  [{:keys [number]}]
  {:number number})

(defn contract-data
  [{:keys [number]}]
  (let [file-name (str "episodes/" number ".edn")]
    (read-edn-file file-name)))

(def swagger-route
  ["/swagger.json"
   {:get {:no-doc  true
          :swagger {:info {:title       "TrustBlocks API"
                           :description "with [malli](https://github.com/metosin/malli) and reitit-ring"}
                    :tags [{:name "contractss", :description "contracts api"}]}
          :handler (swagger/create-swagger-handler)}}])

(defn handle-save-request
  [request]
  (some->> (get-in request [:parameters :body])
           save-contract-data
           (assoc {:status 200} :body)))

(defn handle-read-request
  [request]
  (some->> (get-in request [:parameters :path])
           contract-data
           (assoc {:status 200} :body)))

(def read-response [:map
                    [:number int?]
                    [:hosts string?]])

(def save-response [:map [:number int?]])





(def api-route
  ["/api"
   ["/contracts"
    ["" {:swagger {:tags ["contracts"]}
         :post    {:summary    "Save data for the contract"
                   :parameters {:body [:map [:number int?]]}
                   :responses  {200 {:body save-response}}
                   :handler    handle-save-request}}]
    ["/:number"
     {:swagger {:tags ["contracts"]}
      :get     {:summary    "Fetch data for the specific contract number"
                :parameters {:path [:map [:number int?]]}
                :responses  {200 {:body read-response}}
                :handler    handle-read-request}}]]])

(def app
  (ring/ring-handler
   (ring/router
    [swagger-route
     api-route]
    {:data     {:coercion   rcm/coercion
                :muuntaja   m/instance
                :middleware [swagger/swagger-feature
                             muuntaja/format-middleware
                             parameters/parameters-middleware
                             coercion/coerce-exceptions-middleware
                             coercion/coerce-request-middleware
                             coercion/coerce-response-middleware]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path   "/"
      :config {:validatorUrl     nil
               :operationsSorter "alpha"}})
    (ring/create-default-handler))))
