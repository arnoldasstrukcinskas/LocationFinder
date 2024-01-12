FROM gradle:8.2.1-jdk17 AS build
COPY . .
RUN gradle build

FROM openjdk:17.0.1-jdk-slim
COPY  --from=build build/libs/LocationFinder-0.0.1-SNAPSHOT-plain.jar LocationFinder.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/LocationFinder.jar"]