(ns main 
  (:gen-class
    :method [^:static [handler [String] String]]))

(defn -handler 
  [s] 
  (str "got " s))

