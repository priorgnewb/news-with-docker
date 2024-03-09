FROM openjdk:17-oracle
WORKDIR /news
COPY target/news-0.0.1-SNAPSHOT.jar /news/news-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/news/news-0.0.1-SNAPSHOT.jar"]