# Stage 1: build the application
FROM gradle:7.6.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon --stacktrace --info

# Stage 2: run the application
FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
