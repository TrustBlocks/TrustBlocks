(ns trustblocks.get-cli
  (:require
     [biff.util :as bu]
     [cheshire.core :as cheshire]
     [clojure.java.io :as io]))

(def local-dir "/Users/tmb/Coding/Accord/cicero-template-library/src/")
(def cf-dir "/Users/tmb/coding/trustblocks/commonform.org/forms")

(defn cicero-parse
      "Simple proof of concept for parse from command line -- pretty slow"
      [contract]
      (let [cta (str local-dir contract)]
          (cheshire/parse-string (first (re-find #"(.*)\{[^\\]*"  (bu/sh "cicero" "parse" cta))) true)))

(defn cicero-draft
      "Simple proof of concept for parse from command line -- pretty slow"
      [contract]
      (let [cta (str local-dir contract)]
          (re-find #"(.*)\{[^\\]*"  (bu/sh "cicero" "draft" cta))))


(comment
  
  (cicero-parse "promissory-note")

  (bu/sh "cicero" "parse --sample"  (str local-dir "promissory-note"))
  (bu/sh "cicero" "parse")
  )