FROM maven:3.8.5-openjdk-17-slim
EXPOSE 8080
COPY ./ /app
WORKDIR /app
RUN mvn clean package -DskipTests
CMD ["java", "-jar", "target/expensetracker.jar"]
