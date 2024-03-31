FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_PATH} whoaserver.jar
ENTRYPOINT ["java","-jar","/whoaserver.jar"]