# Expense tracker backend

Java, Spring Boot, MySQL

How to run:

- Start docker mysql database

  `docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:latest`

  - create the schema `expensetracker` if necessary

- maven

  `.\mvnw.cmd spring-boot:run`
