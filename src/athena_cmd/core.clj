(ns athena-cmd.core
  (:gen-class)
  (:require [cheshire.core :refer :all]))

(import '(java.sql DriverManager) 
        'java.util.Properties
        'com.amazonaws.athena.jdbc.AthenaDriver)

(use '[clojure.pprint :only [print-table]])

(defn get_properties
  []
  (let [info (Properties.)
        s3_path_env "ATHENA_S3_PATH"
        s3_path (System/getenv s3_path_env)
        tmp (java.io.File/createTempFile "athena" ".log")]

    (doto info 

      (.put "s3_staging_dir" 
        (if s3_path s3_path 
          (throw (Exception. (str s3_path_env " is not set.")))))

      (.put "aws_credentials_provider_class"
            "com.amazonaws.auth.DefaultAWSCredentialsProviderChain")

      (.put "log_path"
            (.getAbsolutePath tmp))) 

          info))

(defn get_stmt 
  [] 
  (let [athenaURI 
        "jdbc:awsathena://athena.us-east-1.amazonaws.com:443"
        info (get_properties)
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

(defn get_session_id
  [fn]
  (let [ls (clojure.string/split-lines (slurp fn))
        m (map 
            #(re-matches #".*Execution ID: (.*)" %) 
            ls)
        f (filter #(< 0 (count %)) m)]
    (second (first f))))

(defn exec
  [query]
  (let [[stmt info] (get_stmt)
        rs (.executeQuery stmt query)
        f (get info "log_path")]

    (loop [res []
           more (.next rs)]

      (if-not more 

        (do 
          [res 
           (get_session_id f) 
           f])

        (do 
          (recur 
            (conj res (read_row rs))
            (.next rs)))))))

(defn query 
  [file]
  (first (exec (slurp file))))

(defn query-table 
  [file]
  (print-table (first (query file))))

(defn exec-table 
  [query]
  (print-table (first (exec query))))

(defn -main
  [& args]
  (let [file (nth args 0)] 
    (println 
      (generate-string (query file) {:pretty true}))))

