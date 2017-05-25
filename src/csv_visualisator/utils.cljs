(ns csv-visualisator.utils
  (:require [clojure.string :as cstr]))

(defn is-string-valid?
  [check? val]
  (and check?
       (or (cstr/blank? val)
           (re-find #"\D" val)))) ;; match non-digit
