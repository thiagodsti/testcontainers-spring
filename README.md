# TodoApplication testcontainers-spring
![CI](https://github.com/thiagodsti/testcontainers-spring/actions/workflows/ci.yaml/badge.svg?branch=main)
[![Coverage](https://codecov.io/gh/thiagodsti/testcontainers-spring/branch/main/graph/badge.svg?token=0FW8UXB870)](https://codecov.io/gh/thiagodsti/testcontainers-spring)

This is a simple project with springboot + testcontainers + JDBI + Reactive and Postgres.
Everything here is reactive except for the connection with the database.

## Development

### Requirements

- Java 17
- Postgres

### Run

Make sure to have a postgres first
```bash
docker run -d -p 5432:5432 \
    -e POSTGRES_PASSWORD=postgres \
    -e PGDATA=/var/lib/postgresql/data/pgdata \
    postgres
```
or if you want to save the database even when the docker is removed
```bash
docker run -d -p 5432:5432 \
    -e POSTGRES_PASSWORD=postgres \
    -e PGDATA=/var/lib/postgresql/data/pgdata \
    -v ~/demo-flyway:/var/lib/postgresql/data \
    postgres
```
Run the application
```bash
mvn spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui.html

### Tests
```bash
mvn clean verify
```