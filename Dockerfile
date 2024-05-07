FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target/SpringBootAppDemo-1.0-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]