(ns main 
  (:gen-class
   :methods [^:static [handler [Object] String]]))

(defn -handler 
  [obj] 
  (str (.get obj "query")))

