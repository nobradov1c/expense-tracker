FROM maven:3.8.5-openjdk-17
EXPOSE 8080
COPY ./ ./
RUN mvn clean package
CMD ["java", "-jar", "target/expensetracker.jar"]
