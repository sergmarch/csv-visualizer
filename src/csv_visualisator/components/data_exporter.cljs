(ns csv-visualisator.components.data-exporter
  (:require [rum.core :as rum]
            [clojure.string :as cstr]))

(defn- encode-uri
  [data]
  (-> (cstr/join "\n" (map #(cstr/join "," %) data))
      clj->js
      (js/encodeURIComponent)))

(rum/defcs exporter < (rum/local "" ::href)
  [state app-state show-component?]
  (when show-component?
    (let [href (::href state)]
      [:a {:href (str "data:attachment/csv, " @href)
           :class "exporter"
           :on-mouse-enter #(reset! href (encode-uri app-state))
           :download "table.csv"}
       "Download current table as CSV"])))
