JAVA_CMD=/usr/lib/jvm/java-8-openjdk-amd64/bin/java

res=`docker run \
    -ti \
    --rm \
    -v $(pwd):/scripts \
    -w /scripts \
    -e ATHENA_S3_PATH=$ATHENA_S3_PATH \
    mikeplavsky/athena-cmd \
    $JAVA_CMD -jar \
    /athena-cmd/target/uberjar/athena-cmd-0.1.0-SNAPSHOT-standalone.jar \
    $1`

echo "$res"
