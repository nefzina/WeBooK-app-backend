FROM amazoncorretto:21
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package
CMD [ "./mvnw", "spring-boot:run" ]
