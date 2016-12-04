mvn deploy:deploy-file \
    -DgroupId=local \
    -DartifactId=AthenaJDBC41 \
    -Dversion=1.0.0 \
    -Dpackaging=jar \
    -Dfile=lib/AthenaJDBC41-1.0.0.jar \
    -Durl=file:lib
