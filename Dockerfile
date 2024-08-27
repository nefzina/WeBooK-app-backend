FROM amazoncorretto:21
WORKDIR /app
RUN chmod +x ./mvnw
CMD [ "./mvnw", "spring-boot:run" ]