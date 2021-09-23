(ns csv-visualisator.core
  (:require [rum.core :as rum]
            [cljsjs.highcharts]
            [csv-visualisator.state :refer [app-state]]
            [csv-visualisator.components.table :as table]
            [csv-visualisator.components.uploader :as uploader]
            [csv-visualisator.components.chart :as chart]
            [csv-visualisator.components.data-exporter :as data-exporter]))

(enable-console-print!)


(rum/defc static []
  [:div {:class "ST"}
    [:p "Static page"]])

(rum/defc app < rum/reactive
  []
  (let [state (rum/react app-state) ;; for reactive component
        show-component? (seq state)]
    [:div
     [:h2 "csv visualizer"]
     [:div
      [:div.table-wrap
       [:div.table-head
        (uploader/input)
        (data-exporter/exporter state show-component?)]
       (table/table show-component?)]
      (when show-component? (chart/pie-chart))]]))

(rum/mount (app) (.getElementById js/document "app"))

