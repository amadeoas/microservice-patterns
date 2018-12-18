# Documentation Apps
A Spring Boot microservice, which provides a central point to obtain the documentation of all the microservices API registered.


## Build Process
Build the application by executing code below.

```bash
mvn package
```

Build the Docker image from running the package command above by executing the code below.

```bash
docker build -t documentation-apps .
```

You can see the list of all the docker images on your system using the command below.

```bash
docker image ls
```
The command above should show the container 'documentation-apps' within the presented list.


## Run Docker Image
Once it has been built a docker image, see steps above under 'Build Process', you can run it using docker run command like:

```bash
docker run -p 5000:9093 documentation-apps
```
