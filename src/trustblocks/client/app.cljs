(ns trustblocks.client.app
  (:require [biff.client :as bc]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [rum.core :as rum]
            [trustblocks.client.app.components :as c]
            [trustblocks.client.app.db :as db]
            [trustblocks.client.app.mutations :as m]
            [trustblocks.client.app.routes :as r]
            [trustblocks.client.app.system :as s]))

(defn ^:export mount []
  (rum/mount (c/main) (js/document.querySelector "#app")))

(defn ^:export init []
  (reset! s/system
    (bc/init-sub {:handler #(m/handler % (second (:?data %)))
                  :sub-results db/sub-results
                  :subscriptions db/subscriptions}))
  (rfe/start!
    (rf/router r/client-routes)
    #(reset! db/route %)
    {:use-fragment false})
  (mount))
