FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/ecommerce-0.0.1-SNAPSHOT.jar /app/ecommerce-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]
