(ns csv-visualisator.state)

(def sample-data [["Company" "Income"] ["Google" "100"] ["Attendify" "1000"] ["Uber" "500"] ["Pied Piper" "750"]])

(def app-state (atom sample-data))
