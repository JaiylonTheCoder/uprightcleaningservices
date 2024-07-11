# Use the Eclipse Temurin 21 JDK image for building the application
FROM eclipse-temurin:21-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the application source code to the container
COPY . .

# Run Maven to clean and package the application, skipping tests
RUN ./mvnw clean package -DskipTests

# Use the OpenJDK 21 JDK slim image for running the application
FROM openjdk:21-jdk-slim

# Copy the packaged JAR file from the build stage to the runtime stage
COPY --from=build /app/target/uprightcleaningservices-0.0.1-SNAPSHOT.jar uprightcleaningservices.jar

# Expose port 8080 for the application
EXPOSE 8080

# Set environment variables for database connection
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/upright_cleaning_services_DB
ENV DB_USERNAME=root
ENV DB_PASSWORD=password

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "uprightcleaningservices.jar"]
