FROM clojure

ADD . /athena-cmd
WORKDIR /athena-cmd

RUN lein deps
RUN lein uberjar
RUN lein repl

CMD lein repl
