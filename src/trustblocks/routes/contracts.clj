(ns trustblocks.routes.contracts)

(defn routes
  []
  ["/contracts" {:get {:handler (fn [req] {:status 200
                                         :body "hello"})}}])