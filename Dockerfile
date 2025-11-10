
FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/news-aggregator-0.1.0.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
