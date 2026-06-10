# Stage 1 — build the fat JAR inside Maven + JDK 11
FROM maven:3.8.6-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -q
COPY src ./src
RUN mvn package -DskipTests -B -q

# Stage 2 — minimal runtime image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/calculator-service-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
