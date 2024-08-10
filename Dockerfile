# Use the official Maven image to build the Spring Boot app
FROM maven:3.8.1-openjdk-17 as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the project source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -Pprod -DskipTests

# Use a smaller base image to run the Spring Boot app
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar /app/erp_dobemcontabilidade.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/erp_dobemcontabilidade.jar"]
