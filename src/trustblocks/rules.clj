(ns trustblocks.rules
  (:require [biff.crux :refer [authorize]]
            [biff.misc :as misc]
            [malli.core :as m]))

; See https://biff.findka.com/#rules


  (def registry
    {:user/id                :uuid
     :user/email             :string
     :user                   [:map {:closed true}
                              [:crux.db/id :user/id]
                              :user/email]
     :msg/id                 :uuid
     :msg/user               :user/id
     :msg/text               :string
     :msg/sent-at            inst?
     :msg                    [:map {:closed true}
                              [:crux.db/id :msg/id]
                              :msg/user
                              :msg/text
                              :msg/sent-at]
     :contract/contractID    :uuid
     :contract/$class        :string
     :contract/template      :string
     :contract/text          :string
     :contract/model         :string
     :contract/logic         :string
     :contract/partyID       :string
     :contract/stateID       :string
     :contract               [:map
                              [:crux.db/id :contract/contractID]
                              [:contract/$class]
                              [:contract/template ]
                              [:contract/text {:optional true}]
                              [:contract/model {:optional true}]
                              [:contract/logic {:optional true}]
                              [:contract/partyID {:optional true}]
                              [:contract/stateID {:optional true}]]
     :clause/clauseID        :uuid
     :clause/$class          :string
     :clause/template        :string
     :clause/text            :string
     :clause/model           :string
     :clause/logic           :string
     :clause/partyID         :string
     :clause/stateID         :string
     :clause                 [:map
                              [:crux.db/id :clause/clauseID ]
                              [:clause/$class {:optional true}]
                              [:clause/template {:optional true}]
                              [:clause/text {:optional true}]
                              [:clause/model {:optional true}]
                              [:clause/logic {:optional true}]
                              [:clause/partyID {:optional true}]
                              [:clause/stateID {:optional true}]]

     :person/identifier      :string
     :person/additional-name :string
     :person/given-name      :string
     :person/family-name     :string
     :person/address         :string
     :person/telephone       :string
     :person/email           :string
     :person/gender          :string
     :person                 [:map
                              [:person/identifier ]
                              [:person/additional-name {:optional true}]
                              [:person/given-name {:optional true}]
                              [:person/family-name {:optional true}]
                              [:person/address {:optional true}]
                              [:person/telephone {:optional true}]
                              [:person/email {:optional true}]
                              [:person/gender {:optional true}]]
     :entity/identifier      :string
     :entity/type            :string
     :entity/business-name   :string
     :entity/category        :string
     :entity/address         :string
     :entity                 [:map
                              [:entity/identifier {:optional true}]
                              [:entity/type {:optional true}]
                              [:entity/business-name {:optional true}]
                              [:entity/category {:optional true}]
                              [:entity/address {:optional true}]]

     })


(def schema (misc/map->MalliSchema
              {:doc-types  [:user :msg :contract :clause :person :entity]
               :malli-opts {:registry (misc/malli-registry registry)}}))

(comment


  (def registry
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
     :contract/$class     :string
     :contract/template   :string
     :contract/text       :string
     :contract/model      :string
     :contract/logic      :string
     :contract/partyID    :string
     :contract/stateID    :string
     :contract            [:map
                           [:crux.db/id :contract/contractID]
                           [:contract/$class]
                           [:contract/template {:optional true}]
                           [:contract/text {:optional true}]
                           [:contract/model {:optional true}]
                           [:contract/logic {:optional true}]
                           [:contract/partyID {:optional true}]
                           [:contract/stateID {:optional true}]]
     :clause/clauseID     :uuid
     :clause/$class       :string
     :clause/template     :string
     :clause/text         :string
     :clause/model        :string
     :clause/logic        :string
     :clause/partyID      :string
     :clause/stateID      :string
     :clause              [:map
                           [:crux.db/id :clause/clauseID]
                           [:clause/$class]
                           [:clause/template {:optional true}]
                           [:clause/text {:optional true}]
                           [:clause/model {:optional true}]
                           [:clause/logic {:optional true}]
                           [:clause/partyID {:optional true}]
                           [:clause/stateID {:optional true}]]
     })

  )

(comment


  (def reg (misc/malli-registry registry))
  (m/form sch)
  (def s (m/form sch))

  (def sch [:map
            {:title "Address"}
            [:id string?]
            [:tags [:set keyword?]]
            [:address
             [:map
              [:street string?]
              [:city string?]
              [:zip int?]
              [:lonlat [:tuple double? double?]]]]])

  (def person [:map
               [:person/identifier]
               [:person/additional-name {:optional true}]
               [:person/given-name {:optional true}]
               [:person/family-name {:optional true}]
               [:person/address {:optional true}]
               [:person/telephone {:optional true}]
               [:person/email {:optional true}]
               [:person/gender {:optional true}]])

  )

(defmethod authorize [:msg :create].,
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
