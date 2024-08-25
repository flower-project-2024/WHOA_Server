FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} whoaserver.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/whoaserver.jar"]
