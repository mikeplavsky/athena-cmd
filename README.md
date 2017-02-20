When running as command line: 
```bash
./run.sh /scripts/rows.sql
```

For running as AWS Lambda:
```bash
export ROLE=<IAM role which has an access to Athena and S3>
export ATHENA_S3_PATH=<s3 path to athena>
./deploy.sh
```

Lambda expects the input event:
```json
{
    "query": "select count(*) from test"
}
```
