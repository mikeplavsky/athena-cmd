docker run \
    -ti \
    --rm \
    -e ATHENA_S3_PATH=$ATHENA_S3_PATH \
    -v $(pwd):/athena-cmd \
    mikeplavsky/athena-cmd \
    bash
