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

(defn swagger-route []
(swagger-ui/create-swagger-ui-handler
 {:path "/"
  :config {:validatorUrl nil
           :operationsSorter "alpha"}}))

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
     ["swagger.json" {:get swagger-route
                      :nodoc true
                      :swagger {:info {:title "TrustBlocks-api"
                                       :description "Data Driven Law"}}
                      :middleware [swagger/swagger-feature]
                                    :handler (swagger/create-swagger-handler)}]
   auth/routes])


