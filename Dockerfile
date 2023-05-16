FROM amazoncorretto:17-alpine
LABEL authors="bendr"
ARG JAR_FILE=target/*.jar
COPY ./target/ShopMap-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]