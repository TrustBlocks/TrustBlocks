(ns trustblocks.core
  (:require
    [biff.misc :as misc]
    [biff.util :as bu]
    [biff.crux :as bcrux]
    [biff.middleware :as mid]
    [trustblocks.handlers :refer [api]]
    [trustblocks.routes.auth :refer [wrap-authentication]]
    [reitit.swagger :as swagger]
    [trustblocks.swagger :refer [swagger-middleware]]
    [trustblocks.env :refer [use-env]]
    [trustblocks.routes :as routes]
    [trustblocks.rules :refer [schema]])
  (:gen-class))




(comment
  (def components
    [use-env
     misc/use-nrepl
     bcrux/use-crux
  ;; 
     routes/app
     #(update % :biff.sente/event-handler
              bcrux/wrap-db {:node (:biff.crux/node %)})
     misc/use-sente

     bcrux/use-crux-sub-notifier
     misc/use-reitit
     #(update % :biff/handler
              bcrux/wrap-db {:node (:biff.crux/node %)})
  ;;  #(update % :biff/handler wrap-authentication)
  ;; ;;  #(update % :biff/handler [swagger/swagger-feature])
  ;;  mid/use-default-middleware
     #(assoc % :biff.jetty/websockets
             {"/api/chsk" (:biff/handler %)})
     misc/use-jetty])
  )

(def config {:biff/schema              (fn [] schema)
            ;;  :biff.reitit/routes       (fn [] (routes))
         ,   ;;  :biff/on-error            (fn [req] (on-error req))
             :biff.sente/event-handler (fn [event] (api event (:?data event)))
             :biff.middleware/spa-path "/app/"
             :biff/after-refresh       `-main})

(comment
  (defn -main []
    (bu/start-system config components))
  )

