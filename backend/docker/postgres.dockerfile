FROM postgres:14.3-alpine3.16
EXPOSE 5432

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=expensetracker