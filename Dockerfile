FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} pokedex.jar
EXPOSE 5000
CMD ["java", "-jar", "pokedex.jar"]