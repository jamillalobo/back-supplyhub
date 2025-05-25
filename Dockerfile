# --- ESTÁGIO DE BUILD ---
FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean install -DskipTests

# --- ESTÁGIO DE PRODUÇÃO ---
FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
