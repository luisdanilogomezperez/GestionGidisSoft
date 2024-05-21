FROM amazoncorretto:8-alpine-jdk

copy out/GestionGidisSoft.jar gidissoft.jar

ENTRYPOINT ["java" , "-jar" , "/gidissoft.jar"]
