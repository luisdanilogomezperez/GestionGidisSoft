FROM amazoncorretto:8-alpine-jdk

COPY target/gidissoft.jar gidissoft.jar

ENTRYPOINT ["java" , "-jar" , "/gidissoft.jar"]
