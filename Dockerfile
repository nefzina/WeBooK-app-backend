FROM amazoncorretto:21
WORKDIR /app
CMD [ "./mvnw", "spring-boot:run" ]