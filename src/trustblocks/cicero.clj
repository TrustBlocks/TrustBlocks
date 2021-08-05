(ns trustblocks.cicero
    (:require 
       [biff.util :as bu]
       [biff.util.protocols :as proto]
       [biff.crux :as bcrux]
       [arachne.fileset :as fs]
       [clojure.java.io :as io]
       [cybermonday.core :as cm]
       [cybermonday.ir :as ir]
       [malli.provider :as mp]
       [cheshire.core :refer :all]
       [clojure.walk :as walk]
       [malli.json-schema :as json-schema]
       [trustblocks.admin :refer [sys]]
       [trustblocks.rules :refer [schema]]
       [clj-http.client :as client]
       [clojure.string :as str]
       [clojure.data.json :as json]))


;;;;;;;      CLI Experimentation       ;;;;;;

;; Set up local directory


(def local-dir "/Users/tmb/Coding/trustblocks/cicero-template-library/src/")
(def cf-dir "/Users/tmb/coding/trustblocks/commonform.org/forms")
;; run cicero test

(defn run-cicero-cli
      "Simple proof of concept for parse from command line -- pretty slow"
      [contract]
      (let [cta (str local-dir contract)]
           (bu/sh "cicero" "parse" cta :out-enc "UTF-8")))

;;;;;;;;;; Load and parse temp,,lates and samples from local directory  ;;;;;;;;;


(defn get-sample
       "Load sample md from file"
       [k]
       (slurp (str local-dir k "/text/sample.md")))

 (defn get-template
       [k]
       (slurp (str local-dir k "/text/grammar.tem.md")))

 (defn sample-ast
       "Markdown Sample to AST"
       [sample]
       (ir/md-to-ir (get-sample sample)))

 (defn template-ast
       "Markdown Template to AST"
       [sample]
       (ir/md-to-ir (get-template sample)))


  
;;;
;;;;;        Cicero- Server experimentation        ;;;;;;;;
;;;
;; Set up endpoint for local server


(def cicero-end "http://localhost:6001/")

;;; create samplle for dev purposes

(def sample "## Late Delivery and Penalty.\n\n In case of delayed delivery except for Force Majeure cases,\n\"Dan\" (the Seller) shall pay to \"Steve\" (the Buyer) for every 2 days\nof delay penalty amounting to 10.5% of the total value of the Equipment\nwhose delivery has been delayed. Any fractional part of a days is to be\nconsidered a full days. The total amount of penalty shall not however,\nexceed 55% of the total value of the Equipment involved in late delivery.\nIf the delay is more than 15 days, the Buyer is entitled to terminate this Contract.")

;;; Set template lib directory - already set above

;; def local-dir "/Users/tmb/Coding/Accord/cicero-template-library/src/")

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
                       :form-params  {"sample" sample}})))


   (template-ast "promissory-note")

    (sample-ast "promissory-note")

 (defn parse-lib
       "parse sample from library"
       [contract]
       (cicero-parse contract (get-sample contract)))

(parse-lib "latedeliveryandpenalty")
   
  ;; (client/post (str cicero-end "parse" "promissory-note")
  ;;           {:headers
  ;;          {"content-type" "json"
  ;;             "accept"       "json"
  ;;            "as"           "json"
  ;;             "form-params"  {"sample" (parse-lib sample)}}})

;;;; simplify call from local library
;;;; uses the input to retreive template and parse markdown
;
;
;
 (defn normalize-contract [{:keys [contractId] :as parsed-contract}]
       (-> (bu/prepend-keys "contract" parsed-contract)
           (dissoc :contract/contractId)
           (assoc :crux.db/id (java.util.UUID/fromString contractId))))


 (defn parse-normalize-lib
       "parse and normalize sample from library"
       [contract]
       (-> (parse-lib contract)
           (normalize-contract)))

 (defn parse-normalize-with-text
       [contract]
      (-> (parse-lib contract)
           (normalize-contract)
           (dissoc :contract/text)
           (assoc :contract/text (sample-ast contract))))


;; (parse-lib "promissory-note")

(defn make-schema
  "Makes a schema from a parsed K"
  [contract]
  (mp/provide (vector (parse-lib contract)))
  )

;(make-sehema "promissory-note")

(defn make-json-schema
  "Makes json-schema from malli schema"
  [contract]
  (-> (make-schema contract)
      (json-schema/transform)))

(defn make-json-schema-string
  "Makes json-schema from malli schema"
  [contract]
  (-> (make-schema contract)
      (json-schema/transform)
      (generate-string)))


;;; utility predicate to insure contact matches schema declared in Registry


; (proto/valid? schema :contract contract) ; true

;;; Function to put everything together


(defn submit-contract
  "submit contract data to crux"
  [template sample]
  (let [contract (normalize-contract (cicero-parse template sample))]
    (bcrux/submit-tx
     (sys)
     {[:contract (:crux.db/id contract)] contract})))

;; (submit-contract "latedeliveryandpenalty" sample)
(defn validate-contract
  "Validate a contract from the library"
  [contract]
  (proto/valid? schema :contract (parse-normalize-lib contract))) ; true

;; (validate-contract "latedeliveryandpenalty")

(defn set-contract-from-lib
  "get a sample from the lib on disk"
  [sample]
  (let [contract (normalize-contract (parse-lib sample))]
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

;;;;; See awsclient for lambda experiments ;;;;

(comment

  (parse-normalize-with-text "promissory-note")
  (validate-contract "promissory-note")
  (parse-normalize-lib "promissory-note")

  (set-contract-from-lib "promissory-note")
  (make-schema "helloworld")
  (walk/stringify-keys (make-schema "hello-world"))

  ;; Make a Malli schema from a template
  ;; transform it to json-schema

  (json-schema/transform (make-schema "promissory-note") {:pretty true})
  (make-schema "helloworld")
  (generate-string (json-schema/transform (make-schema "helloworld") {:pretty true}))

  (def sch {"type"       "object"
            "properties" {"$class"      {"type" "string"}
                          "name"        {"type" "string"}
                          "clauseId"    {"type" "string"}
                          "$identifier" {"type" "string"}}
            "required"   ["$class" "name" "clauseId" "$identifier"]})

  (parse-string (generate-string (json-schema/transform (make-schema "helloworld"))))
  (generate-string sch)
  (make-schema "helloworld")
  (json-schema/transform (make-schema "helloworld"))
  (parse-string (generate-string sch))
  (cicero-parse "promissory-note" (parse-lib "promissory-note"))
  (normalize-contract (parse-lib "promissory-note"))
  (parse-lib, "promissory-note")
  (mp/provide (vector (parse-lib "promissory-note")))
  (cicero-draft "promissory-note" (parse-lib "promissory-note"))

  (proto/valid? schema :contract (parse-normalize-lib "promissory-note")) ; true
  (make-schema "promissory-note")
  (set-contract "promissory-note" (get-sample "promissory-note"))

  (def contract (normalize-contract (cicero-parse "latedeliveryandpenalty" sample)))
  ;; =>
  {:contract/forceMajeure      true
   :contract/fractionalPart    "days"
   :contract/capPercentage     55
   :contract/seller            "resource:org.accordproject.party.Party#Dan"
   :contract/penaltyPercentage 10.5
   :contract/buyer             "resource:org.accordproject.party.Party#Steve"
   :contract/termination
   {:$class "org.accordproject.time.Duration", :amount 15, :unit "days"}
   :contract/$class
   "org.accordproject.latedeliveryandpenalty.LateDeliveryAndPenaltyContract"
   :crux.db/id                 #uuid "ed3dcee9-2bb7-4871-9c04-0c21ed09b854"
   :contract/$identifier       "ed3dcee9-2bb7-4871-9c04-0c21ed09b854"
   :contract/penaltyDuration
   {:$class "org.accordproject.time.Duration", :amount 2, :unit "days"}}
  (def k2 (parse-lib "promissory-note"))
  (prn k2)
  (proto/valid? schema :contract contract)                  ; true
  (parse-lib "latedeliveryandpenalty")
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
            '{:find  [(pull contract [*])]
              :where [[contract :contract/$class]]}))

  ;;; results from cicero-parse
  ;;;
  (def delta
    {:contractId        "92c57f20-dda0-4511-bd2b-d3f6bb4dfedc"
     :penaltyDuration   {:$class "org.accordproject.time.Duration", :amount 2, :unit "days"}
     :$identifier       "92c57f20-dda0-4511-bd2b-d3f6bb4dfedc"
     :$class            "org.accordproject.latedeliveryandpenalty.LateDeliveryAndPenaltyContract"
     :forceMajeure      true
     :termination       {:$class "org.accordproject.time.Duration", :amount 15, :unit "days"}
     :fractionalPart    "days"
     :buyer             "resource:org.accordproject.party.Party#Tom"
     :capPercentage     55
     :penaltyPercentage 10.5
     :seller            "resource:org.accordproject.party.Party#Jacob"})

  (bu/prepend-keys "contract" delta)
  ;;; Curl Request from Docs

  (def sample "## Late Delivery and Penalty.\n\n In case of delayed delivery except for Force Majeure cases,\n\"Dan\" (the Seller) shall pay to \"Steve\" (the Buyer) for every 2 days\nof delay penalty amounting to 10.5% of the total value of the Equipment\nwhose delivery has been delayed. Any fractional part of a days is to be\nconsidered a full days. The total amount of penalty shall not however,\nexceed 55% of the total value of the Equipment involved in late delivery.\nIf the delay is more than 15 days, the Buyer is entitled to terminate this Contract.")

  (hello-sample "")

  ;;; Grab a json model from the model repository
  (client/get "https://models.accordproject.org/accordproject/party.json"))


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
         (println (str/split-lines k)))
