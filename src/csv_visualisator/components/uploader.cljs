(ns csv-visualisator.components.uploader
  (:require [rum.core :as rum]
            [clojure.string :as cstr]
            [csv-visualisator.state :refer [app-state]]
            [goog.labs.format.csv :as csv]))

(def ^:private max-file-size 4096)

(def ^:private max-file-size-kb (/ max-file-size 1024))

(defn- file-size-warning
  [target]
  (do
    (reset! app-state [])
    (set! (.-value target) "")
    (js/alert
      (str "Your file is too large! Max file size is "
           max-file-size-kb
           "KB"))))

(defn- display-csv-data
  [e]
  (let [target (.. e -target)
        file (-> (.-files target)
                 array-seq
                 first)]
    (when file
      (if (> (.-size file) max-file-size)
        (file-size-warning target)
        (let [reader (js/FileReader.)]
          (set! (.-onload reader)
                (fn [e]
                  (try
                    (let [result (-> (.. e -target -result)
                                     csv/parse
                                     js->clj)]
                      (reset! app-state result))
                    (catch js/Error err
                      (set! (.-value target) "")
                      (js/alert "Something went wrong. Please check your file and try again")))))
          (.readAsText reader file))))))

(rum/defc input
  []
  [:div.uploader-wrap {:data-size max-file-size-kb}
   [:input {:type "file"
            :accept ".csv"
            :on-change display-csv-data}]])

