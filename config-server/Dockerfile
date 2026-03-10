# start with the base image containing java runtime
FROM openjdk:17-jdk-slim

LABEL maintainer="tarunmarisetti"

# Install curl & grep for Docker healthchecks
RUN apt-get update && apt-get install -y --no-install-recommends curl grep \
    && rm -rf /var/lib/apt/lists/* \

WORKDIR /app
COPY target/config-server-0.0.1-SNAPSHOT.jar config-service.jar

ENTRYPOINT ["java","-jar","config-service.jar"]
