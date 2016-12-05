When running as command line: 
```bash
./run.sh rows.sql
```

For running as AWS Lambda:
```bash
export ROLE=<IAM role which has an access to Athena and S3>
./deploy.sh
```

Lambda expects the input event:
```json
{
    "query": "select count(*) from test"
}
```
