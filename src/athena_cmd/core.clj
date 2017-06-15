(ns athena-cmd.core
  (:gen-class)
  (:require [cheshire.core :refer :all]))

(import '(java.sql DriverManager) 
        'java.util.Properties
        'com.amazonaws.athena.jdbc.AthenaDriver)

(use '[clojure.pprint :only [print-table]])

(def log_path 
  (.getAbsolutePath 
    (java.io.File/createTempFile "athena" ".log")))

(defn get_properties
  [folder]
  (let [info (Properties.)
        s3_path_env "ATHENA_S3_PATH"
        s3_path (str (System/getenv s3_path_env))]

    (doto info 

      (.put "s3_staging_dir" 
        (if s3_path (str s3_path folder) 
          (throw (Exception. (str s3_path_env " is not set.")))))

      (.put "aws_credentials_provider_class"
            "com.amazonaws.auth.DefaultAWSCredentialsProviderChain")

      (.put "log_path" log_path)) 

          info))

(defn get_stmt 
  [folder] 
  (let [
        aws_region_env (str (System/getenv "AWS_DEFAULT_REGION"))
        athenaURI 
            (str 
              "jdbc:awsathena://athena." 
              aws_region_env 
              ".amazonaws.com:443")
        info (get_properties folder)
        conn (DriverManager/getConnection athenaURI info)
        stmt (.createStatement conn)] [stmt info]))

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

  [query & {:keys [folder]
            :or {folder ""}}]

  (let [[stmt info] (get_stmt folder)
        rs (.executeQuery stmt query)
        f (get info "log_path")]

    (loop [res []
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

(defn query-table 
  [file]
  (print-table (query file)))

(defn exec-table 
  [query]
  (print-table (exec query)))

(defn -main
  [& args]
  (let [file (nth args 0)] 
    (println 
      (generate-string (query file) {:pretty true}))))

