# Build stage
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
RUN chmod +x gradlew
COPY src ./src
RUN ./gradlew clean bootJar --no-daemon -i  # -i pour le mode info/debug

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /deploy
COPY --from=build /app/build/libs/csdmp-*.jar /deploy/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/deploy/app.jar"]