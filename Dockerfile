FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG JAR_FILE=target/tictactoe-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} tictactoe.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tictactoe.jar"]