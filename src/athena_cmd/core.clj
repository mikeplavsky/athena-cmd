(ns athena-cmd.core
  (:gen-class)
  (:require [cheshire.core :refer :all]))

(import '(java.sql DriverManager) 
        'java.util.Properties
        'com.amazonaws.athena.jdbc.AthenaDriver)

(def athenaURI 
  "jdbc:awsathena://athena.us-east-1.amazonaws.com:443")

(def info (Properties.))

(def s3_path_env "ATHENA_S3_PATH")
(def s3_path (System/getenv s3_path_env))

(.put info "s3_staging_dir" 
      (if s3_path s3_path 
        (throw (Exception. (str s3_path_env " is not set.")))))

(.put info "aws_credentials_provider_class"
           "com.amazonaws.auth.DefaultAWSCredentialsProviderChain")

(.put info "log_path"
           "/tmp/athenajdbc.log")

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

(defn exec
  [query]
  (let [rs (.executeQuery stmt query)
        m (.getMetaData rs)]

    (loop [
           res []
           more (.next rs)]

      (if-not more 
        res
        (do 
          (recur 
            (conj res (read_row rs))
            (.next rs)))))))

(defn query 
  [file]
  (exec (slurp file)))

(defn -main
  [& args]
  (let [file (nth args 0)] 
    (println 
      (generate-string (query file) {:pretty true}))))

