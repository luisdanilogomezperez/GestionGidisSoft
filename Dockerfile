FROM openjdk:8-jdk-alpine

COPY target/gidissoft.jar gidissoft.jar

ENTRYPOINT ["java", "-jar", "gidissoft.jar"]
