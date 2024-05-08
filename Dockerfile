FROM openjdk:17-jdk-slim
EXPOSE 8081
ADD target/SpringBootAppDemo-1.0-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]