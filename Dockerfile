FROM amazoncorretto:21
WORKDIR /app
COPY --chown=1000:1000 mvnw .
COPY --chown=1000:1000 .mvn .mvn
COPY --chown=1000:1000 pom.xml .
RUN chmod +x ./mvnw
COPY --chown=1000:1000 src src
CMD [ "./mvnw", "spring-boot:run" ]