(ns trustblocks.cicero
  (:require [biff.util :as bu]
            [portal.api :as p]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(defn run-cicero
  "Simple proof of concept for parse from command line -- pretty slow"
  [contract]
  (let [cta (str "templates/" contract)]
    (bu/sh "cicero" "parse" cta :out-enc "UTF-8")))




(comment

(p/open)                                                    ;Open Portal Browser
(add-tap #'p/submit)                                        ;add p as portal target
(def k (run-cicero "promissory-note"))                      ; returns text with two lines at front
(tap> (run-cicero "promissory-note"))                       ; have to strip off 1st 2 lines
(println (str/trim-newline (run-cicero "promissory-note"))) ; doesn't work returns whole thing in vector
(println k)
(str/split k )
(println (str/split-lines k))

;; Cut and paste removing first two lines ----

(def jk (json/read-str "{\n  \"$class\": \"org.accordproject.promissorynote.PromissoryNoteContract\",\n  \"amount\": {\n    \"$class\": \"org.accordproject.money.MonetaryAmount\",\n    \"doubleValue\": 1000,\n    \"currencyCode\": \"USD\"\n  },\n  \"date\": \"2018-01-30T00:00:00.000-04:00\",\n  \"maker\": \"Daniel\",\n  \"interestRate\": 3.8,\n  \"individual\": true,\n  \"makerAddress\": \"1 Main Street\",\n  \"lender\": \"Clause Inc.\",\n  \"legalEntity\": \"CORP\",\n  \"lenderAddress\": \"246 5th Ave, 3rd Fl, New York, NY 10001\",\n  \"principal\": {\n    \"$class\": \"org.accordproject.money.MonetaryAmount\",\n    \"doubleValue\": 500,\n    \"currencyCode\": \"USD\"\n  },\n  \"maturityDate\": \"2019-01-20T00:00:00.000-04:00\",\n  \"defaultDays\": 90,\n  \"insolvencyDays\": 90,\n  \"jurisdiction\": \"New York, NY\",\n  \"contractId\": \"c331ee37-16ee-49c7-923d-e9014fbf6d4c\",\n  \"$identifier\": \"c331ee37-16ee-49c7-923d-e9014fbf6d4c\"\n} "))

(tap> jk)                                                   ;Works in Portal

)
