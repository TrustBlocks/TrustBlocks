(ns trustblocks.concerto
  (:require [biff.util :as bu]
            [biff.util.protocols :as proto]
            [biff.crux :as bcrux]
            [malli.core :as m]
            [malli.transform :as mt]
            [crux.api :as crux]
            [juxt.jinx-alpha-2 :as jinx]
            [trustblocks.admin :refer [sys]]
            [trustblocks.rules :refer [schema]]
            [clj-http.client :as client]
            [portal.api :as p]
            [clojure.string :as str]))

;; Concerto Model handling

(def library "https://models.accordproject.org/accordproject/")


(defn get-model
  [model]
  (:body (client/get (str library "/" model ".json")
                     {:content-type :json
                      :accept       :json
                      :as           :json})))

(get-model "party")

(comment

  (get-model "contract")
  )
