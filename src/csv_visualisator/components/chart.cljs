(ns csv-visualisator.components.chart
  (:require [rum.core :as rum]
            [csv-visualisator.utils :as utils]
            [csv-visualisator.state :refer [app-state]]))

(defn chart-config [dom-node]
  (let [[[company-name income] & rest] @app-state
        chart-data (mapv (fn [[key val]]
                           {:name key
                            :y (if (utils/is-string-valid? true val)
                                 -1
                                 (js/parseInt val))})
                         rest)]
    {:chart {:type "pie"
             :renderTo dom-node}
     :title {:text (str company-name " / " income)}
     :tooltip {:pointFormat "<b>{point.percentage:.1f}%</b>"}
     :plotOptions {:pie {:cursor "pointer"
                         :animation false
                         :size 250}}
     :credits {:enabled false}
     :series [{:colorByPoint true
               :data chart-data}]}))


(defn- mount-chart! [opts]
  (js/Highcharts.Chart. (clj->js opts) nil))

(rum/defc pie-chart < {:did-mount (fn [state]
                                    (mount-chart! (chart-config (rum/dom-node state)))
                                    state)
                       :will-update (fn [state]
                                      (mount-chart! (chart-config (rum/dom-node state)))
                                      state)}
  []
  [:div.chart])

