FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/football-league-rakesh.jar

WORKDIR /opt/app

COPY ${JAR_FILE} football-league-rakesh.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","football-league-rakesh.jar"]
