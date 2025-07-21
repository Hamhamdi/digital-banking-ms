FROM openjdk:21-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven
RUN mvn package -DskipTests

EXPOSE 9999

CMD ["java", "-jar", "target/gateway-service-1.0-SNAPSHOT.jar"]