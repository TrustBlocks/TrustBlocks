(ns trustblocks.core
  (:require
    [biff.misc :as misc]
    [biff.util :as bu]
    [biff.crux :as bcrux]
    [biff.middleware :as mid]
    [trustblocks.handlers :refer [api]]
    [trustblocks.routes.auth :refer [wrap-authentication]]
    [trustblocks.env :refer [use-env]]
    [trustblocks.routes :refer [routes on-error]]
    [trustblocks.rules :refer [schema]])
  (:gen-class))

(def components
  [use-env
   misc/use-nrepl
   bcrux/use-crux
   #(update % :biff.sente/event-handler
            bcrux/wrap-db {:node (:biff.crux/node %)})
   misc/use-sente
   bcrux/use-crux-sub-notifier
   misc/use-reitit
   #(update % :biff/handler
            bcrux/wrap-db {:node (:biff.crux/node %)})
   #(update % :biff/handler wrap-authentication)
   mid/use-default-middleware
   #(assoc % :biff.jetty/websockets
           {"/api/chsk" (:biff/handler %)})
   misc/use-jetty])

(def config {:biff/schema              (fn [] schema)
             :biff.reitit/routes       (fn [] (routes))
             :biff/on-error            (fn [req] (on-error req))
             :biff.sente/event-handler (fn [event] (api event (:?data event)))
             :biff.middleware/spa-path "/app/"
             :biff/after-refresh       `-main})

(defn -main []
  (bu/start-system config components))
