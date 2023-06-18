FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY ./target/demoSpringRestaurant-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]