# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

LABEL version="1.0"
LABEL description="This is the employee service implementing Swagger"
# Add Maintainer Info
LABEL maintainer="aas@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 9092 available to the world outside this container (private and public mapping)
EXPOSE 9092:9092

ENV SPRING_PROFILES_ACTIVE docker

# The application's jar file
ARG JAR_FILE=target/employee-application-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} employee-application.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/employee-application.jar"]
