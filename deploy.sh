aws lambda create-function \
    --function-name athena-cmd \
    --handler main::handler \
    --runtime java8 \
    --memory 512 \
    --timeout 10 \
    --role $ROLE \
    --zip-file fileb://./target/uberjar/athena-cmd-0.1.0-SNAPSHOT-standalone.jar
