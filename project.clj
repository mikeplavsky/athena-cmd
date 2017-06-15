(defproject athena-cmd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"local" "file:lib"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [com.amazonaws/aws-java-sdk-core "1.11.63"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [local/AthenaJDBC41 "1.1.0"]
                 [cheshire "5.6.3"]]
  :main ^:skip-aot athena-cmd.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot 
                       :all
                       :repositories {"local" "file:lib"}}})
