(ns main 
  (:require [cheshire.core :refer :all])
  (:gen-class
   :methods [^:static 
             [handler 
              [Object 
               com.amazonaws.services.lambda.runtime.Context] 
              Object]]))

(require 'athena-cmd.core)

(defn -handler 
  [obj ctx] 

  (let [query (.get obj "query")
        request_id (.getAwsRequestId ctx)]

    (print (str request_id ": "))
    (println (generate-string {"query" query}))

    (time {"res" (athena-cmd.core/exec query :folder request_id)
     "request_id" request_id})))

