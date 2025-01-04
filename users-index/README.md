# Run the application
You can run this spring in choice of your IDE or using the following command:
```shell
./gradlew bootRun
```
Once it has consumed the user messages  from the Kafka topic, you can below curl command to query Elasticsearch to verify the data.

## Query Elasticsearch
```shell
curl localhost:9200/_search -H "Content-Type: application/json" -d '{"query":{"match": {"name":"Tahir Naqasj"}}}'
``` 