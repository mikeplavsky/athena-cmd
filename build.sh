if ! [ -f lib/AthenaJDBC41-1.1.0.jar ]; then
    aws s3 cp s3://athena-downloads/drivers/AthenaJDBC41-1.1.0.jar lib/
fi

docker build -t mikeplavsky/athena-cmd . 
