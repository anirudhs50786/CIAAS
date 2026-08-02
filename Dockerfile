# Build stage
FROM gradle:8-jdk21 AS build
WORKDIR /src
# copy gradle and source; adjust to include only necessary files for faster build
COPY --chown=gradle:gradle . .
RUN gradle clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
ARG JAR_FILE=app.jar

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the built jar from the build stage
COPY --from=build /src/build/libs/*.jar /app/${JAR_FILE}
RUN chown appuser:appgroup /app/${JAR_FILE}
USER appuser

EXPOSE 8081
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]