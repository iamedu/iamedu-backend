FROM openjdk:17-alpine

ADD target/iamedu-backend-0.0.1-SNAPSHOT-standalone.jar /iamedu-backend/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/iamedu-backend/app.jar"]
