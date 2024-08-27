FROM amazoncorretto:21
#WORKDIR /app
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#RUN chmod +x ./mvnw
#COPY src src
RUN chmod +x mvnw && chown -R 1000:1000 /app
USER 1000:1000
CMD [ "./mvnw", "spring-boot:run" ]