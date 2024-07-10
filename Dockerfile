FROM eclipse-temurin:21-jdk AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /targer/uprightcleaningservices-0.0.1-SNAPSHOT.jar uprightcleaningservices.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "uprightcleaningservices.jar"]