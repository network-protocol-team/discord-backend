FROM openjdk:17-jdk

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
