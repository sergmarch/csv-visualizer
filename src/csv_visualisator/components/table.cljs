(ns csv-visualisator.components.table
  (:require [rum.core :as rum]
            [csv-visualisator.utils :as utils]
            [csv-visualisator.state :refer [app-state]]))

(rum/defc table-input
  ([initial-value value index target-value-first?]
   (table-input initial-value value index target-value-first? false))
  ([initial-value value index target-value-first? number-cell?]
   (let [show-warning? (utils/is-string-valid? number-cell? initial-value)]
     [:input {:type "text"
              :class (when show-warning? "is-error")
              :value initial-value
              :on-change (fn [e]
                           (let [target-value (.. e -target -value)]
                             (swap! app-state assoc index
                                    (if target-value-first?
                                      [target-value value]
                                      [value target-value]))))}])))

(rum/defc table
  [show-component?]
  (when show-component?
    (let [[[key val] & rest] @app-state]
      [:table
       [:thead
        [:tr
         [:th
          (table-input key val 0 true)]
         [:th
          (table-input val key 0 false)]]]
       [:tbody
        (map-indexed (fn [i [key val]]
                       (let [curr-index (inc i)]
                         [:tr {:key i}
                          [:td
                           (table-input key val curr-index true)]
                          [:td
                           (table-input val key curr-index false true)]]))
                     rest)]])))

