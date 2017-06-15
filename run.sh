JAVA_CMD=/usr/lib/jvm/java-8-openjdk-amd64/bin/java

res=`docker run \
    -ti \
    --rm \
    -v $(pwd):/athena-cmd \
    -w /athena-cmd \
    -e AWS_DEFAULT_REGION=$AWS_DEFAULT_REGION \
    -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
    -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
    -e ATHENA_S3_PATH=$ATHENA_S3_PATH \
    mikeplavsky/athena-cmd \
    $JAVA_CMD -jar \
    /athena-cmd/target/uberjar/athena-cmd-0.1.0-SNAPSHOT-standalone.jar \
    $1`

echo "$res"
