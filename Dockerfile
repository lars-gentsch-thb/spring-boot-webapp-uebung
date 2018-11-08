FROM ubuntu:18.04
MAINTAINER docker@thb.de

RUN apt-get update
RUN apt-get install openjdk-8-jre-headless -y

# Gradle
#ADD ./build/libs/your-app-1.0.jar /service.jar

# Maven
ADD ./target/demo-0.0.1-SNAPSHOT.jar /service.jar

ENTRYPOINT java -jar /service.jar
EXPOSE 8080

