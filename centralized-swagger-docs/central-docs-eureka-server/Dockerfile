# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

LABEL version="1.0"
LABEL description="This is the central register for the services"
# Add Maintainer Info
LABEL maintainer="aas@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8761 available to the world outside this container
EXPOSE 8761

# The application's jar file
ARG JAR_FILE=target/central-docs-eureka-server-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} central-docs-eureka-server.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/central-docs-eureka-server.jar"]
