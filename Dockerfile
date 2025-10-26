# Dockerfile for Spring Boot Application
# Requires pre-built JAR file
# Build first: mvn clean package

FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="docker@thb.de"
LABEL description="Spring Boot Demo Application"

WORKDIR /app

# Copy the pre-built JAR file
COPY target/demo-0.0.1-SNAPSHOT.jar /app/service.jar

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/ || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
