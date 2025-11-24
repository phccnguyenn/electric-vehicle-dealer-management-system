FROM maven:3.9.9-eclipse-temurin-21 AS evd_builder
WORKDIR /app/evd
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre
# FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=evd_builder /app/evd/target/*.jar app.jar
EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
# ENTRYPOINT ["java", "-jar", "app.jar"]
