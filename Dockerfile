# Use an official OpenJDK runtime as a parent image
FROM openjdk:24-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY build/libs/*.jar app.jar

# Expose the port your app runs on (default is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]