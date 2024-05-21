FROM amazoncorretto:8-alpine-jdk

COPY out/artifacts/GestionGidisSoft_jar/GestionGidisSoft.jar gidissoft.jar

ENTRYPOINT ["java" , "-jar" , "/gidissoft.jar"]
