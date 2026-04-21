FROM eclipse-temurin:25-jdk

WORKDIR /app

# Copiar todo el proyecto (incluyendo gradlew)
COPY . /app/

# Verificar que gradlew existe (para debugging)
RUN ls -la gradlew

# Dar permisos
RUN chmod +x gradlew

# Convertir a formato Unix (para evitar problemas de Windows)
RUN sed -i 's/\r$//' gradlew

# Compilar el JAR
RUN ./gradlew bootJar --no-daemon

EXPOSE 6001

ENTRYPOINT ["java", "-jar", "build/libs/fairticket-0.0.1-SNAPSHOT.jar"]