FROM openjdk:17-jdk-slim
LABEL authors="zarip"
EXPOSE 8081
ADD target/TestContainer-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]