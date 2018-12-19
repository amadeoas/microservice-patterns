# Documentation Apps
A Spring Boot microservice, which provides a central point to obtain the documentation of all the microservices API registered.


## Usage
To access the documentation of all the registered services use the following URL.

```
localhost:9093
```

To access the documentation of this service in JSON format use the following URL.

```
localhost:9093/v2/api-docs
```

To access the documentation of an specified registered service use the following URL, where '__servicename__' is the name of the service as it is in the registry.

```
localhost:9093/service/{servicename}
```

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

You can use the -d option in docker run command to run the container in the background, and gives you the container ID:

```bash
docker run -d -p 5000:8080 documentation-apps
```

A list of all containers running in the system can be shown by executing the following command:

```bash
docker container ls
```
