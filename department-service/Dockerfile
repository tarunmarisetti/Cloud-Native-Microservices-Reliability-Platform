# start with the base image containing java runtime
FROM openjdk:17-jdk-slim

LABEL maintainer="tarunmarisetti"

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/department-service-0.0.1-SNAPSHOT.jar department-service.jar

ENTRYPOINT ["java","-jar","department-service.jar"]
