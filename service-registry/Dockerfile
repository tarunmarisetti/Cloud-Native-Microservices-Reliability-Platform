FROM openjdk:17-jdk-slim

# Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/Service-Registry-0.0.1-SNAPSHOT.jar service-registry.jar

ENTRYPOINT ["java", "-jar", "service-registry.jar"]
