(ns main 

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

    {:res (athena-cmd.core/exec query)
     :request_id request_id}))

