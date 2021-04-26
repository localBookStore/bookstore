FROM openjdk:11-jre-slim
WORKDIR /app/backend
COPY build/libs/*.jar ./build/libs/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]