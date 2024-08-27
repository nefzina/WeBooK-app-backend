FROM amazoncorretto:21
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x ./mvnw
COPY src src
CMD [ "./mvnw", "spring-boot:run" ]