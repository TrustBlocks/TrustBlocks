(ns commonform
  (:require
      [clj-http.client :as client]
      [cybermonday.core :as cm]
      [cybermonday.ir :as ir]
   ))

(comment

 ;; Permalink: https://commonform.org/kemitchell/software-covered-versions/1e2u

 ;; Github: https://github.com/commonform/commonform.org/blob/main/forms/kemitchell/software-covered-versions/1e2u.md

  ;; Github raw: https://raw.githubusercontent.com/commonform/commonform.org/main/forms/kemitchell/software-covered-versions/1e2u.md

  ;; Raw String:

  ;; ---
  ;; published: '2019-01-31'
  ;;     digest: fd503a2ca1e9e58ea57fbaad65516f3f21bbe2fc9cbef46ea9ada92d32c4e3f9
  ;; ---

  ;; **Covered Versions** depend on the _Order Form_:

  ;; - If the _Order Form_ specifies a particular version, _Covered Versions_ include only that version.

  ;; - If the _Order Form_ specifies a version range, such as "1.y.z", then _Covered Versions_ include the latest _Generally Available_ version in that range on the date of this agreement, as well as any new versions within that range that _Vendor_ makes _Generally Available_ during the term of this agreement.

  ;; - If the _Order Form_ does not specify a particular version or version range, then _Covered Versions_ include the latest _Generally Available_ version on the date of this agreement, as well as any new versions that _Vendor_ makes _Generally Available_ during the term of this agreement.

  ;; - A version of the _Software_ is **Generally Available** if _Vendor_ offers to the public for use in production systems. Versions that _Vendor_ designates with labels like "Alpha", "Beta", "Preview", "Testing", and "Preview" are not _Generally Available_.
  
)

(def gitraw (client/get "https://raw.githubusercontent.com/commonform/commonform.org/main/forms/kemitchell/software-covered-versions/1e2u.md "))
(def md-stub "https://raw.githubusercontent.com/commonform/commonform.org/main/forms/kemitchell/")
(def commonform "software-covered-versions/1e2u.md")

(print gitraw)

(defn get-cf
  [commonform]
  (client/get (:body)(str md-stub commonform)))

(get-cf  "software-covered-versions/1e2u.md")