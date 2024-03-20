FROM openjdk:17-alpine
ARG JAR_PATH=/*.jar
COPY ${JAR_PATH} whoaserver.jar
ENTRYPOINT ["java","-jar","/whoaserver.jar"]