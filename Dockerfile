FROM amazoncorretto:21
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
CMD [ "./mvnw", "spring-boot:run" ]