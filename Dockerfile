FROM clojure

ADD . /athena-cmd
WORKDIR /athena-cmd

RUN lein deps
RUN lein uberjar

CMD java -cp lib/*:target/uberjar/* athena_cmd/core
