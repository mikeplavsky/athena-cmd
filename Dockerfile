FROM clojure

RUN apt-get update
RUN apt-get install -y jq

RUN curl -O \
    https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein  

RUN mv lein /usr/bin && chmod +x /usr/bin/lein
ENV LEIN_ROOT 1

RUN lein

RUN apt-get install -y maven

ADD . /athena-cmd
WORKDIR /athena-cmd

RUN ./repo.sh

ENV JAVA_CMD $JAVA_HOME/bin/java

RUN lein deps
RUN lein uberjar

