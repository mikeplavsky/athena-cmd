FROM clojure

RUN apt-get update
RUN apt-get install -y jq

ADD . /athena-cmd
WORKDIR /athena-cmd

RUN lein deps
RUN lein uberjar
RUN lein repl

CMD lein repl
