FROM mysql:8.0.28-oracle
EXPOSE 3306
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=expensetracker
# COPY world.sql /docker-entrypoint-initdb.d/
