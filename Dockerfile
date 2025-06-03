FROM eclipse-temurin:21-jdk as build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests
RUN mv target/*.jar app.jar

FROM eclipse-temurin:21-jre
ARG PORT=8080
ENV PORT=${PORT}
WORKDIR /home/runtime
RUN useradd -m runtime
COPY --from=build /app/app.jar .
RUN chown runtime:runtime app.jar
USER runtime
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
