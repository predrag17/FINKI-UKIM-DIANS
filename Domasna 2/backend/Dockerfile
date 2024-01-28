#FROM openjdk:20
#WORKDIR /app
#COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar
#EXPOSE 8080
#CMD ["java", "-jar", "backend.jar"]

FROM openjdk:20 AS builder
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar /app/

FROM debian:buster-slim
RUN apt-get update && \
    apt-get install -y postgresql-client && \
    rm -rf /var/lib/apt/lists/*


ENV POSTGRES_DB=wine
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=Martiniste#1234
ENV POSTGRES_HOST=localhost
ENV POSTGRES_PORT=5432

EXPOSE 8080
EXPOSE 5432

CMD ["java", "-jar", "backend.jar"]