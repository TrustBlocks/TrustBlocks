(ns trustblocks.cicero
  (:require [biff.util :as bu]
            [clj-http.client :as client]
            [portal.api :as p]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(defn run-cicero
  "Simple proof of concept for parse from command line -- pretty slow"
  [contract]
  (let [cta (str "templates/" contract)]
    (bu/sh "cicero" "parse" cta :out-enc "UTF-8")))


;;;  Version 2 using cicero-server --- running locally Localhost:6001

(comment

  ;;; Grab a json model from the model repository
 (client/get "https://models.accordproject.org/accordproject/party.json")

  (client/post "http://localhost:6001/parse/latedeliveryandpenalty"
               {:content-type :json
                :accept       :json
                :body-type    :json
                :data  '{"sample": "##Late Delivery and Penalty. \n \n In case of delayed delivery except for Force Majeure cases, \n \"Dan \" (the Seller) shall pay to \"Steve \" (the Buyer) for every 2 days \nof delay penalty amounting to 10.5 % of the total value of the Equipment \nwhose delivery has been delayed. Any fractional part of a days is to be \nconsidered a full days. The total amount of penalty shall not however, \nexceed 55 % of the total value of the Equipment involved in late delivery. \nIf the delay is more than 15 days, the Buyer is entitled to terminate this Contract."}
                '
                })

  curl --request POST \
  --url http://localhost:6001/parse/latedeliveryandpenalty \
  --header 'accept: application/json' \
  --header 'content-type: application/json' \
  --data '{
           "sample": "## Late Delivery and Penalty.\n\n In case of delayed delivery except for Force Majeure cases,\n\"Dan\" (the Seller) shall pay to \"Steve\" (the Buyer) for every 2 days\nof delay penalty amounting to 10.5% of the total value of the Equipment\nwhose delivery has been delayed. Any fractional part of a days is to be\nconsidered a full days. The total amount of penalty shall not however,\nexceed 55% of the total value of the Equipment involved in late delivery.\nIf the delay is more than 15 days, the Buyer is entitled to terminate this Contract."}
  '


  )


(comment  -- CLI Tests
  (run-cicero "promissory-note")
(p/open)                                                    ;Open Portal Browser
(add-tap #'p/submit)                                        ;add p as portal target
(def k (run-cicero "promissory-note"))  ; returns text with two lines at front
(println k)
(tap> (run-cicero "promissory-note"))                       ; have to strip off 1st 2 lines
(println (str/trim-newline (run-cicero "promissory-note"))) ; doesn't work returns whole thing in vector
(println k)
(str/split k )
(println (str/split-lines k))

;; Cut and paste removing first two lines ----

(def jk (json/read-str "{\n  \"$class\": \"org.accordproject.promissorynote.PromissoryNoteContract\",\n  \"amount\": {\n    \"$class\": \"org.accordproject.money.MonetaryAmount\",\n    \"doubleValue\": 1000,\n    \"currencyCode\": \"USD\"\n  },\n  \"date\": \"2018-01-30T00:00:00.000-04:00\",\n  \"maker\": \"Daniel\",\n  \"interestRate\": 3.8,\n  \"individual\": true,\n  \"makerAddress\": \"1 Main Street\",\n  \"lender\": \"Clause Inc.\",\n  \"legalEntity\": \"CORP\",\n  \"lenderAddress\": \"246 5th Ave, 3rd Fl, New York, NY 10001\",\n  \"principal\": {\n    \"$class\": \"org.accordproject.money.MonetaryAmount\",\n    \"doubleValue\": 500,\n    \"currencyCode\": \"USD\"\n  },\n  \"maturityDate\": \"2019-01-20T00:00:00.000-04:00\",\n  \"defaultDays\": 90,\n  \"insolvencyDays\": 90,\n  \"jurisdiction\": \"New York, NY\",\n  \"contractId\": \"c331ee37-16ee-49c7-923d-e9014fbf6d4c\",\n  \"$identifier\": \"c331ee37-16ee-49c7-923d-e9014fbf6d4c\"\n} "))

(tap> jk)                                                   ;Works in Portal

)
