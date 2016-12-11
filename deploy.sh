aws lambda delete-function \
    --function-name athena-cmd \

aws lambda create-function \
    --function-name athena-cmd \
    --handler main::handler \
    --runtime java8 \
    --environment Variables={ATHENA_S3_PATH=$ATHENA_S3_PATH} \
    --memory 512 \
    --timeout 50 \
    --role $ROLE \
    --zip-file fileb://./target/uberjar/athena-cmd-0.1.0-SNAPSHOT-standalone.jar
