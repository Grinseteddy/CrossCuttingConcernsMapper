FROM openjdk:10-jre-slim
COPY ./target/mapper-0.0.1-SNAPSHOT.jar /tmp/app.jar
WORKDIR /tmp/
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]