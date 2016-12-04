(ns athena-cmd.core
  (:gen-class)
  (:require [cheshire.core :refer :all]))

(import '(java.sql DriverManager) 
        'java.util.Properties
        'com.amazonaws.athena.jdbc.AthenaDriver)

(def athenaURI 
  "jdbc:awsathena://athena.us-east-1.amazonaws.com:443")

(def info (Properties.))

(.put info "s3_staging_dir" 
           "s3://aws-athena-query-results1/")

(.put info "aws_credentials_provider_class"
           "com.amazonaws.auth.InstanceProfileCredentialsProvider")

(.put info "log_path"
           "./.athena/athenajdbc.log")

(def conn (DriverManager/getConnection athenaURI info))
(def stmt (.createStatement conn))

(defn read_row 
  [rs]
  (let [m (.getMetaData rs)
        cnt (.getColumnCount m)]

    (reduce 
      #(assoc %1 (.getColumnName m %2) 
                (.getString rs %2)) 
                {}
                (range 1 (+ 1 cnt)))))

(defn -main
  [& args]

  (let [query (slurp (nth args 0))
        rs (.executeQuery stmt query)
        m (.getMetaData rs)]

    (loop [
           res []
           more (.next rs)]

      (if-not more 
        (println 
          (generate-string res {:pretty true})) 
        (do 
          (recur 
            (conj res (read_row rs))
            (.next rs)))))))
