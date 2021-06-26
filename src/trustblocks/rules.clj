(ns trustblocks.rules
  (:require [biff.crux :refer [authorize]]
            [biff.misc :as misc]))

; See https://biff.findka.com/#rules

(def registry
  [:map
   {:user/id             :uuid
    :user/email          :string
    :user                [:map {:closed true}
                          [:crux.db/id :user/id]
                          :user/email]
    :msg/id              :uuid
    :msg/user            :user/id
    :msg/text            :string
    :msg/sent-at         inst?
    :msg                 [:map {:closed true}
                          [:crux.db/id :msg/id]
                          :msg/user
                          :msg/text
                          :msg/sent-at]
    :contract/contractID :uuid
    :contract/template   :string
    :contract/text       :string
    :contract/model      :string
    :contract/logic      :string
    :contract/partyID    :string
    :contract/stateID    :string
    :contract            [:map
                          [:crux.db/id :contract/contractID]
                          [:contract/template {:optional true}]
                          [:contract/text {:optional true}]
                          [:contract/model {:optional true}]
                          [:contract/logic {:optional true}]
                          [:contract/partyID {:optional true}]
                          [:contract/stateID {:optional true}]]
    :clause/clauseID     :uuid
    :clause/template     :string
    :clause/text         :string
    :clause/model        :string
    :clause/logic        :string
    :clause/partyID      :string
    :clause/stateID      :string
    :clause              [:map
                          [:crux.db/id :clause/clauseID]
                          [:clause/template {:optional true}]
                          [:clause/text {:optional true}]
                          [:clause/model {:optional true}]
                          [:clause/logic {:optional true}]
                          [:clause/partyID {:optional true}]
                          [:clause/stateID {:optional true}]]
    }])

(def schema (misc/map->MalliSchema
              {:doc-types  [:user :msg]
               :malli-opts {:registry (misc/malli-registry registry)}}))

(defmethod authorize [:msg :create]
  [{:keys [biff/uid]} {:keys [msg/user]}]
  (= uid user))

(defmethod authorize [:msg :delete]
  [{:keys [biff/uid]} {:keys [msg/user]}]
  (= uid user))

(defmethod authorize [:msg :query]
  [_ _]
  true)

(defmethod authorize [:user :get]
  [{:keys [biff/uid]} {:keys [crux.db/id]}]
  (= uid id))

(defmethod authorize [:user :update]
  [{:keys [biff/uid]} before {:keys [crux.db/id] :as after}]
  (and (= uid id)
       (apply = (map #(dissoc % :user/foo) [before after]))))
