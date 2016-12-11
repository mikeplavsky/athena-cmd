(ns main 
  (:gen-class
   :methods [^:static [handler [Object] Object]]))

(require '[cheshire.core :refer :all])

(require 'athena-cmd.core)
(require 'clojure.java.shell)

(defn -handler 
  [obj] 
  (let [query (.get obj "query")
        tmp (java.io.File/createTempFile "query" ".log")
        path (.getAbsolutePath tmp)]
    (spit path query)
    (parse-string 
      (:out 
        (clojure.java.shell/sh 
          "java" "-cp" "." "athena_cmd.core" path)))))

