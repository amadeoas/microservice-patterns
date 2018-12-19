# Central Document Eureka Server
A Spring Boot microservice, which acts a registry server of available microservice.


## Usage
To access the list of all the registered services use the following URL.

```
localhost:8761
```

## Build Process
Build the application by executing code below.

```bash
mvn package
```

Build the Docker image from running the package command above by executing the code below.

```bash
docker build -t central-docs-eureka-server .
```

You can see the list of all the docker images on your system using the command below.

```bash
docker image ls
```
The command above should show the container 'central-docs-eureka-server' within the presented list.


## Run Docker Image
Once it has been built a docker image, see steps above under 'Build Process', you can run it using docker run command like:

```bash
docker run -p 5000:8761 central-docs-eureka-serve
```

You can use the -d option in docker run command to run the container in the background, and gives you the container ID:

```bash
docker run -d -p 5000:8080 central-docs-eureka-serve
```

A list of all containers running in the system can be shown by executing the following command:

```bash
docker container ls
```
