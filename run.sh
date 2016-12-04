res=`docker run \
    -ti \
    --rm \
    mikeplavsky/athena-cmd \
    java -cp lib/*:target/uberjar/* \
    athena_cmd/core \
    $1`

echo $res
