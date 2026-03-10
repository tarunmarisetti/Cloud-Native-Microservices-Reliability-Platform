# start with the base image containing java runtime
FROM openjdk:17-jdk-slim

LABEL maintainer="tarunmarisetti"

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

ENTRYPOINT ["java","-jar","api-gateway.jar"]
