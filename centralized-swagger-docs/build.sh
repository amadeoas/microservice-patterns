#!/bin/sh
cd central-docs-eureka-server
./build.sh

cd ..
cd documentation-app
./build.sh

cd ..
cd employee-application
./build.sh

cd ..
cd person-application
./build.sh

cd ..
docker image ls
