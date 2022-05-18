FROM harisekhon/ubuntu-java
RUN mkdir sdk-ubuntu
ADD sdk-ubuntu sdk-ubuntu/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.config.additional-location=config/SerialNumbers.properties" ,"/app.jar"]
