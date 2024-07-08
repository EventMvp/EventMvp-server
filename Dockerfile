# Stage 1: Build the application
FROM maven:3.9-sapmachine-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests
RUN echo "done"

# Stage 2: Run the application
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]