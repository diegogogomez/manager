# Usar OpenJDK 17 como base
FROM openjdk:17-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo gradle wrapper
COPY gradlew .
COPY gradle/ gradle/

# Copiar archivos de configuración de gradle
COPY build.gradle .
COPY settings.gradle .

# Dar permisos de ejecución al gradle wrapper
RUN chmod +x ./gradlew

# Descargar dependencias (esto se cachea en Docker)
RUN ./gradlew dependencies --no-daemon

# Copiar código fuente
COPY src/ src/

# Construir la aplicación
RUN ./gradlew build -x test --no-daemon

# Crear directorio para la aplicación
RUN mkdir -p /app/run

# Copiar el JAR construido
RUN cp build/libs/*.jar /app/run/app.jar

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/run/app.jar"]