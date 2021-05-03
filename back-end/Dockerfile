FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine as builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootBuildimage --imageName=hangs0908/bookstore-backend --stacktrace

FROM openjdk:11-jre-slim
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]