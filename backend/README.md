# Expense tracker backend

Java, Spring Boot, PostgreSQL

How to run:

- `cp .env-example .env`
- `docker-compose build`
- `docker-compose up`

Note:

- Please run this once to initialize your databse:

  - `INSERT INTO roles (id, title) VALUES (1, 'ADMIN');`

  - `INSERT INTO roles (id, title) VALUES (2, 'USER');`

- TODO: next time use enums for roles

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
