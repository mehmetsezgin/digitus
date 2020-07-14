FROM openjdk:8-jdk-alpine

EXPOSE 8080

ARG JAR_FILE=target/login.jar
ADD ${JAR_FILE} login.jar

ENTRYPOINT ["java", "-jar", "/login.jar"]