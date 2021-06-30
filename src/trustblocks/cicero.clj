(ns trustblocks.cicero
  (:require [biff.util :as bu]
            [biff.util.protocols :as proto]
            [biff.crux :as bcrux]
            [crux.api :as crux]
            [trustblocks.admin :refer [sys]]
            [trustblocks.rules :refer [schema]]
            [clj-http.client :as client]
            [portal.api :as p]
            [clojure.string :as str]
            [clojure.data.json :as json]))


;;;
;;;  Stub for CLI Calls  - not presently used

(defn run-cicero-cli
  "Simple proof of concept for parse from command line -- pretty slow"
  [contract]
  (let [cta (str "templates/" contract)]
    (bu/sh "cicero" "parse" cta :out-enc "UTF-8")))

;; Set up endpoint for local server

(def cicero-end "http://localhost:6001/")

;;; Basic Parse this takes text in Markdown - that conforms to the template -
;;; It returns the Data in JSON

(defn cicero-parse
  "Parsing API call"
  [contract sample]
  (:body
    (client/post (str cicero-end "parse/" contract)
                 {:content-type :json
                  :accept       :json
                  :as           :json
                  :form-params  {"sample" sample}}))
  )

(defn normalize-contract [{:keys [contractId] :as parsed-contract}]
  (-> (bu/prepend-keys "contract" parsed-contract)
      (dissoc :contract/contractId)
      (assoc :crux.db/id (java.util.UUID/fromString contractId))))


;;; Basic draft function - this takes a template and JSON to return a Markdown Document

(defn cicero-draft
  "Draft API Call"
  [contract data]
  (:body
    (client/post (str cicero-end "draft/" contract)
                 {:content-type :json
                  :form-params  {"data" data}})))

;;;  Version 2 using cicero-server --- running locally Localhost:6001

(comment


  ;;; Getting into database
  ;;;


  (def contract (normalize-contract (cicero-parse "latedeliveryandpenalty" sample)))
  ;; =>
  {:contract/forceMajeure true,
   :contract/fractionalPart "days",
   :contract/capPercentage 55,
   :contract/seller "resource:org.accordproject.party.Party#Dan",
   :contract/penaltyPercentage 10.5,
   :contract/buyer "resource:org.accordproject.party.Party#Steve",
   :contract/termination
   {:$class "org.accordproject.time.Duration", :amount 15, :unit "days"},
   :contract/$class
   "org.accordproject.latedeliveryandpenalty.LateDeliveryAndPenaltyContract",
   :crux.db/id #uuid "ed3dcee9-2bb7-4871-9c04-0c21ed09b854",
   :contract/$identifier "ed3dcee9-2bb7-4871-9c04-0c21ed09b854",
   :contract/penaltyDuration
   {:$class "org.accordproject.time.Duration", :amount 2, :unit "days"}}

  (proto/valid? schema :contract contract) ; true

  (bcrux/submit-tx
    (sys)
    {[:contract (:crux.db/id contract)] contract})

  (let [{:keys [biff.crux/db]} (sys)]
    (crux/q @db
            '{:find [(pull contract [*])]
              :where [[contract :contract/$class]]}))


  (bcrux/submit-tx  ;; ClassCastException
    (assoc sys :biff.crux/authorize true)
    (cicero-parse "latedeliveryandpenalty" sample))

  (bcrux/submit-tx  ;; TX doesn't math schema
    sys
    (cicero-parse "latedeliveryandpenalty" sample))

  ;;; results from cicero-parse
  ;;;
  (assoc sys :biff.crux/authorize true)
  (def delta
    {:contractId        "92c57f20-dda0-4511-bd2b-d3f6bb4dfedc",
     :penaltyDuration   {:$class "org.accordproject.time.Duration", :amount 2, :unit "days"},
     :$identifier       "92c57f20-dda0-4511-bd2b-d3f6bb4dfedc",
     :$class            "org.accordproject.latedeliveryandpenalty.LateDeliveryAndPenaltyContract",
     :forceMajeure      true,
     :termination       {:$class "org.accordproject.time.Duration", :amount 15, :unit "days"},
     :fractionalPart    "days",
     :buyer             "resource:org.accordproject.party.Party#Tom",
     :capPercentage     55,
     :penaltyPercentage 10.5,
     :seller            "resource:org.accordproject.party.Party#Jacob"})


  ;;; Curl Request from Docs

  (def sample "## Late Delivery and Penalty.\n\n In case of delayed delivery except for Force Majeure cases,\n\"Dan\" (the Seller) shall pay to \"Steve\" (the Buyer) for every 2 days\nof delay penalty amounting to 10.5% of the total value of the Equipment\nwhose delivery has been delayed. Any fractional part of a days is to be\nconsidered a full days. The total amount of penalty shall not however,\nexceed 55% of the total value of the Equipment involved in late delivery.\nIf the delay is more than 15 days, the Buyer is entitled to terminate this Contract.")


  (cicero-draft "latedeliveryandpenalty" delta)


  (cicero-draft "latedeliveryandpenalty" draft)

  (cicero-parse "latedeliveryandpenalty" sample)

  ;;; Proper format from Jacob

  (:body
    (client/post "http://localhost:6001/draft/latedeliveryandpenalty"
                 {:content-type :json
                  :accept       :json
                  :as           :json
                  :form-params  {"draft" draft}}))




  ;;; Grab a json model from the model repository
  (client/get "https://models.accordproject.org/accordproject/party.json")

  )


(comment -- CLI Tests
         (run-cicero "promissory-note")
         (p/open)                                           ;Open Portal Browser
         (add-tap #'p/submit)                               ;add p as portal target
         (def k (run-cicero "promissory-note"))             ; returns text with two lines at front
         (println k)
         (tap> (cicero-parse "latedeliveryandpenalty" sample))
         (tap> (run-cicero "promissory-note"))              ; have to strip off 1st 2 lines
         (println (str/trim-newline (run-cicero "promissory-note"))) ; doesn't work returns whole thing in vector
         (println k)
         (str/split k)
         (println (str/split-lines k))


         ;; Cut and paste removing first two lines ----

         (def jk (json/read-str "{\n  \"$class\": \"org.accordproject.promissorynote.PromissoryNoteContract\",\n  \"amount\": {\n    \"$class\": \"org.accordproject.money.MonetaryAmount\",\n    \"doubleValue\": 1000,\n    \"currencyCode\": \"USD\"\n  },\n  \"date\": \"2018-01-30T00:00:00.000-04:00\",\n  \"maker\": \"Daniel\",\n  \"interestRate\": 3.8,\n  \"individual\": true,\n  \"makerAddress\": \"1 Main Street\",\n  \"lender\": \"Clause Inc.\",\n  \"legalEntity\": \"CORP\",\n  \"lenderAddress\": \"246 5th Ave, 3rd Fl, New York, NY 10001\",\n  \"principal\": {\n    \"$class\": \"org.accordproject.money.MonetaryAmount\",\n    \"doubleValue\": 500,\n    \"currencyCode\": \"USD\"\n  },\n  \"maturityDate\": \"2019-01-20T00:00:00.000-04:00\",\n  \"defaultDays\": 90,\n  \"insolvencyDays\": 90,\n  \"jurisdiction\": \"New York, NY\",\n  \"contractId\": \"c331ee37-16ee-49c7-923d-e9014fbf6d4c\",\n  \"$identifier\": \"c331ee37-16ee-49c7-923d-e9014fbf6d4c\"\n} "))

         (tap> jk)                                          ;Works in Portal
         )
