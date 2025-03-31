FROM openjdk:22
WORKDIR /app
COPY ./target/football-standings-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "football-standings-0.0.1-SNAPSHOT.jar"]