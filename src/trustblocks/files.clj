(ns trustblocks.files
  (:require
   [arachne.fileset :as fs]
   [cheshire.core :refer :all]
   [clojure.string :as str]
   [clojure.java.io :as io]))

(def local-dir "/Users/tmb/Coding/trustblocks/cicero-template-library/src/")
(def cf-dir "/Users/tmb/coding/trustblocks/commonform.org/forms")

;; (def cicero-fileset (fs/add (fs/fileset) (io/file local-dir)))
;; (def cf-fileset (fs/add (fs/fileset) (io/file cf-dir)))

;; parse out the top level directory
(defn get-dir [fileset]
  (nth (re-find #"(.*?)\/"  fileset) 1))


;; use get-dir to pull the toplevel directory out
 (defn dir-set [fileset]
     (into #{} (map get-dir (fs/ls fileset))))

(def dirs (dir-set cicero-fileset))

  ;;  (dir-set cicero-fileset)
;; (dir-set cicero-fileset)
;;      (def dir-list (dir-set cicero-fileset))

;; (dir-set cicero-fileset)
(comment
  
  (dir-set cicero-fileset)

  (str/split "latedeliveryandpenalty-optional-this/logic/logic.ergo" #"\/")
  
(map #(cicero-package cicero-fileset %) (dir-set cicero-fileset))
(file-seq (io/file local-dir))


  )



(comment
  [:map
   [:crux.db/id uuid?]
   [:contract/contractId uuid?]
   [:contract/clauseId uuid?]
   [:contract/title string?]
   [:contract/$class string?]
   [:contract/template string?]
   [:contract/text {:optional true} string?]
   [:contract/model {:optional true} string?]
   [:contract/logic {:optional true} string?]
   [:contract/data {:optional true} map?]
   [:contract/package {:optional true} map?]
   [:contract/partyId {:optional true} string?]
   [:contract/stateId {:optional true} string?]]
  
[:map
 [:crux.db/id #uuid "6beb3db0-a949-4e2b-bb2e-00e2acd7f0e9"]
 [:contract/contractId #uuid "6beb3db0-a949-4e2b-bb2e-00e2acd7f0e9"]
 [:contract/clauseId uuid?]
 [:contract/title "demandforecast"]
 [:contract/$class string?]
 [:contract/template string?]
 [:contract/text "demandforecast/text/sample.md"]
 [:contract/model "demandforecast/model/model.cto"]
 [:contract/logic "demandforecast/logic/logic.ergo"]
 [:contract/data {:optional true} map?]
 [:contract/package {:description "A sample demandforecast clause." 
                     :license "Apache-2.0"
                     :displayName "Demand Forecast"
                     :name "demandforecast" 
                     :scripts {:test "cucumber-js test -r .cucumber.js"}
                     :accordproject {:template "clause"
                                     :cicero "^0.22.0"} 
                     :keywords ["demand" "forecast" "clause" "purchaser" "supplier" "supply" "commitment" "product" "calendar"], 
                     :author "Accord Project" 
                     :devDependencies {:cucumber "^5.1.0"} 
                     :version "0.15.0"}]
 [:contract/partyId {:optional true} string?]
 [:contract/stateId {:optional true} string?]]

  
(slurp (fs/content cicero-fileset "demandforcast/package.json")true)
)

(defn cicero-cta [dir]
  (let
   [cta (fs/add (fs/fileset) (io/file (str local-dir dir)))]
    (fs/ls cta)))

(defn cicero-fs-cta 
  [fileset dir]
  (re-find (re-pattern dir)))

 
(cicero-cta "promissory-note")
(cicero-cta "payment-upon-delivery")

(defn cicero-package
  [fileset path] 
   (parse-string (slurp (fs/content fileset (str path "/package.json"))) true))

(cicero-package cicero-fileset "promissory-note")

(cicero-cta "promissory-note") (str cf-dir "/promissory-note/" (nth (cicero-cta "promissory-note") 2))

 (comment

(map (cicero-package cicero-fileset )  )

   (fs/ls cicero-fileset)
   
(def dir "supplyagreement")
   
   (def dir "safte.*")
(dir-set cicero-fileset)
(def dirset (dir-list cicero-fileset))

   (get-file dir "safte/model/model.cto ")
  
   (print dirset)
   (print dirset)

   (map get-file (dir-set cicero-fileset) (fs/ls cicero-fileset))
   
   
 (defn get-file
   [dir link]
   (let 
    [pat (str dir ".*")]
        (re-find (re-pattern pat) link))) 

(fs/filter (re-find #"supplyagreement.*") (fs/ls cicero-fileset))
   
   (get-file )
   (fs/ls cicero-fileset)

 (print (fs/ls cicero-fileset))
   
   (fs/ls c-fileset)
   (parse-string (slurp (fs/content cicero-fileset "demandforecast/package.json")) true)

  
   (cicero-package cicero-fileset "supplyagreement")

   (map cicero-package (dir-set cicero-fileset) (fs/ls cicero-fileset))

 
 (dir-set cicero-fileset)
  (fs/ls cicero-fileset
   (defn get-cta 
     [fileset contract]
     )
(print cicero-fileset)

(defn file
  "Returns a java.io.File of the underlying file at the given path. Note that the given file MUST NOT be
   modified, at the risk of corrupting the fileset.
   Returns nil if the path does not exist in the fileset."
  [fileset path]
  (when-let [tmpf (get-in fileset [:tree path])]
    (impl/-file tmpf)))
   
(defn content
  "Opens and returns a java.io.InputStream of the contents of the file at the given path, or nil
   if the path does not exist."
  [fileset path]
  (when-let [f (file fileset path)]
    (io/input-stream f)))   

  ) 
   
;; pull in the entire src directory -- all the template files


 (fs/ls cf-fileset)

;;; Count the files
   (get-dir «cf-dir)
   
 (count (fs/ls cf-fileset))
 
; pull text from template library

 (fs/ls cicero-fileset)
   
;;; Source directory listing

   (re-find #"(.*?)\/" "promissory-note/logic/logic.ergo")

   (defn get-dir [fileset]
     (nth (re-find #"(.*?)\/"  fileset) 1))

   (map get-dir (fs/ls cicero-fileset))

   (defn dir-set [fileset]
     (into #{} (map get-dir (fs/ls fileset))))
   
     (def dir-list (dir-set cicero-fileset))
   
(dir-set cf-fileset)
   (print dir-list)
           (get-dir cicero-fileset)
   
   )