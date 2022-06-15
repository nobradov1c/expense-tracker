# Expense tracker backend

Java, Spring Boot, PostgreSQL

How to run:

- `cp .env-example .env`
- `docker-compose build`
- `docker-compose up`

or manually:

- Start docker postgresdb database

  - `docker build -f docker/postgres.dockerfile -t mypostgredbimage:latest ./docker`
  - `docker run --name mypostgredb -p 5432:5432 -d mypostgredbimage:latest`

- maven

  - `.\mvnw.cmd spring-boot:run`

build:

- `.\mvnw clean package`
- `java -jar target/target/expensetracker.jar`

Docs:

- [http://server:port/context-path/swagger-ui.html](http://server:port/context-path/swagger-ui.html)
- [http://server:port/context-path/v3/api-docs](http://server:port/context-path/v3/api-docs)

Quick links:

- [http://localhost:8081/api/swagger-ui/index.html](http://localhost:8081/api/swagger-ui/index.html)
- [http://localhost:8081/api/v3/api-docs](http://localhost:8081/api/v3/api-docs) - json
