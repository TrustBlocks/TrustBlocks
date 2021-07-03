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
;;;  Stub for CLI Calls  - not presently used but may be used for Markdown transform or other items

(defn run-cicero-cli
  "Simple proof of concept for parse from command line -- pretty slow"
  [contract]
  (let [cta (str "templates/" contract)]
    (bu/sh "cicero" "parse" cta :out-enc "UTF-8")))

;; Set up endpoint for local server

(def cicero-end "http://localhost:6001/")


;;; Set template lib directory

(def local-dir "/Users/tmb/Coding/Accord/cicero-template-library/src/")

;; pull text from template library

(defn get-sample
  "Load sample md from file"
  [k]
  (slurp (str local-dir k "/text/sample.md")))

;;; Basic Parse this takes text in Markdown - that conforms to the template -
;;; It returns the Data in JSON with an ID
;;; At least in the present version the document to parse has to have key of sample
;;; The contract is the library template


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


;;; simplify call from local library
;;


(defn parse-lib
  "parse sample from library"
  [contract]
  (cicero-parse contract (get-sample contract)))


;;; Normalizes contract data for Crux
;;; converts the created UUID to a Valid UUID for Crux

(defn normalize-contract [{:keys [contractId] :as parsed-contract}]
  (-> (bu/prepend-keys "contract" parsed-contract)
      (dissoc :contract/contractId)
      (assoc :crux.db/id (java.util.UUID/fromString contractId))))


;;; utility predicate to insure contact matches schema declared in Registry


; (proto/valid? schema :contract contract) ; true

;;; Function to put everything together


(defn set-contract
  [template sample]
  (let [contract (normalize-contract (cicero-parse template sample))]
    (bcrux/submit-tx
      (sys)
      {[:contract (:crux.db/id contract)] contract})))

;;; Basic draft function - this takes a template and JSON to return a Markdown Document
;;; This could be used to create a new contract instance


(defn cicero-draft
  "Draft API Call"
  [contract data]
  (:body
    (client/post (str cicero-end "draft/" contract)
                 {:content-type :json
                  :form-params  {"data" data}})))

;;;  Version 2 using cicero-server --- running locally Localhost:6001

(comment

  (proto/valid? schema :contract contract) ; true

  (set-contract "latedeliveryandpenalty" sample)

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

(cicero-parse "latedeliveryandpenalty" sample)

(normalize-contract delta)
  (-> (bu/prepend-keys "contract" (:keys delta))
      (dissoc :contract/contractId))

  (:keys delta)

  (dissoc :contract/contractId)

;;; Submit transaction

  (bcrux/submit-tx
    (sys)
    {[:contract (:crux.db/id contract)] contract})

;;; Query

  (let [{:keys [biff.crux/db]} (sys)]
    (crux/q @db
            '{:find [(pull contract [*])]
              :where [[contract :contract/$class]]}))

  ;;; results from cicero-parse
  ;;;
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

(bu/prepend-keys "contract" delta)
  ;;; Curl Request from Docs

  (def sample "## Late Delivery and Penalty.\n\n In case of delayed delivery except for Force Majeure cases,\n\"Dan\" (the Seller) shall pay to \"Steve\" (the Buyer) for every 2 days\nof delay penalty amounting to 10.5% of the total value of the Equipment\nwhose delivery has been delayed. Any fractional part of a days is to be\nconsidered a full days. The total amount of penalty shall not however,\nexceed 55% of the total value of the Equipment involved in late delivery.\nIf the delay is more than 15 days, the Buyer is entitled to terminate this Contract.")

  (hello-sample "")

  ;;; Grab a json model from the model repository
  (client/get "https://models.accordproject.org/accordproject/party.json")

  )


(comment -- CLI Tests using portal

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

         )
