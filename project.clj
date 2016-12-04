(defproject athena-cmd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :resource-paths ["lib/AthenaJDBC41-1.0.0.jar"]
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [com.amazonaws/aws-java-sdk "1.11.63"]]
  :main ^:skip-aot athena-cmd.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
