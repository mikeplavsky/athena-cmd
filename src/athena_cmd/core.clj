(ns athena-cmd.core
  (:gen-class))

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

    (doall 
      (map 

        #(println (.getColumnName m %) 
                  (.getString rs %)) 

                  (range 1 (+ 1 cnt))))))

(defn -main
  [& args]

  (println "executing query")

  (let [query (slurp (nth args 0))
        rs (.executeQuery stmt query)
        m (.getMetaData rs)]

    (loop [more (.next rs)]
      (if-not more 
        (println "done") 
        (do 
          (read_row rs)
          (recur (.next rs)))))))
