JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

docker run \
    -ti \
    --rm \
    -e JAVA_CMD=$JAVA_HOME/bin/java \
    -v $(pwd):/athena-cmd \
    mikeplavsky/athena-cmd \
    bash
