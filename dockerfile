# Etapa 1: construir el jar
FROM gradle:8.5-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test --no-daemon

# Etapa 2: empaquetar solo el jar en la imagen final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]