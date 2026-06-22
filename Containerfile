FROM eclipse-temurin:21-jre-jammy
RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*
WORKDIR /app

RUN useradd -ms /bin/bash springuser
USER springuser

COPY inventory-service/build/libs/inventory-service.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=staging", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "app.jar"]

EXPOSE 8080
