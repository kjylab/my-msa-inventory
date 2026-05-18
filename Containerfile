FROM eclipse-temurin:21.0.9_10-jdk-jammy AS build
WORKDIR /app

ARG GITHUB_ACTOR
ARG GITHUB_TOKEN

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY inventory-event/build.gradle.kts inventory-event/
COPY inventory-service/build.gradle.kts inventory-service/
COPY inventory/build.gradle.kts inventory/

RUN GITHUB_ACTOR=${GITHUB_ACTOR} GITHUB_TOKEN=${GITHUB_TOKEN} ./gradlew dependencies --no-daemon

COPY . .

RUN GITHUB_ACTOR=${GITHUB_ACTOR} GITHUB_TOKEN=${GITHUB_TOKEN} ./gradlew :inventory-service:bootJar -x test --no-daemon --refresh-dependencies

FROM eclipse-temurin:21.0.9_10-jre-jammy
WORKDIR /app

RUN useradd -ms /bin/bash springuser
USER springuser

COPY --from=build /app/inventory-service/build/libs/inventory-service.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=staging", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "app.jar"]

EXPOSE 8080
