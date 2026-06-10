# Stage 1 — build the fat JAR (ARM64-compatible)
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -q
COPY src ./src
RUN mvn package -DskipTests -B -q

# Stage 2 — minimal JRE runtime
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /app/target/calculator-service-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
