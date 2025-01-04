# Search Index Processing with Postgres CDC, Kafka and Elasticsearch
This project demonstrates how to process data from a Postgres database using Debezium CDC, Kafka and Elasticsearch. The project consists of the following components:
- Postgres Database
- Debezium Connector
- Kafka
- Elasticsearch
- Spring Boot Application
- Docker
- Docker Compose
- Gradle

One service(users-management) has API to add users to the Postgres database. This also has an API to update the user.
Another service(users-index) listens to the Kafka topic and indexes the user data in Elasticsearch.

## Prerequisites
Run the docker-compose file to start the Postgres, Kafka and Elasticsearch services.
```shell
docker-compose up -d
```
### Create Topic in Debezium Connector
You can con configure the Debezium connector to listen to the Postgres database and create a topic for the table:  users.
The following command creates a topic for the users table in the Postgres database.

```shell   
curl --location 'http://localhost:8083/connectors' \
   --header 'Accept: application/json' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "cdc-debezium-connector",
   "config": {
       "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
       "database.hostname": "cdc-postgres.cdcprocessor.orb.local",
       "database.port": "5432",
       "database.user": "test",
       "database.password": "test",
       "database.dbname": "test",
       "database.server.id": "184054",
       "table.include.list": "public.users",
       "topic.prefix": "users-table-topic"
   }
}'
```
You can get your database.hostname from docker and update the above curl command accordingly. It can also be IP of your machine.
## User APIs
The users-management service has the following APIs:
- POST /users - Add a user
- PUT /users/{id} - Update a user
- GET /users - Get all users

### Add a User
```shell
{
    "name": "Kane Williamson",
    "email": "user@gmail.com",
    "phone":"+322222233444"
}
```


## Query Elasticsearch to verify the data
```shell
curl localhost:9200/_search -H "Content-Type: application/json" -d '{"query":{"match": {"name":"Kane"}}}'
``` 

