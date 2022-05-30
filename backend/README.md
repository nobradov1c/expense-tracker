# Expense tracker backend

Java, Spring Boot, MySQL

How to run:

- Start docker mysql database

  `docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:latest`

  - create the schema `expensetracker` if necessary

- maven

  `.\mvnw.cmd spring-boot:run`

Docs:

- [http://server:port/context-path/swagger-ui.html](http://server:port/context-path/swagger-ui.html)
- [http://server:port/context-path/v3/api-docs](http://server:port/context-path/v3/api-docs)

Quick links:

- [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- [http://localhost:8080/api/v3/api-docs](http://localhost:8080/v3/api-docs) - json
