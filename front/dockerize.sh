#!/bin/sh

IMAGE_NAME="vue-app"
IMAGE_TAG="front"
CONTAINER_NAME="vue-app-container"
HOST_HTTP_PORT=80
HOST_HTTPS_PORT=443
CONTAINER_HTTP_PORT=80
CONTAINER_HTTPS_PORT=443
SERVER_NAME=${SERVER_NAME:-"kTrip.com"}

echo "build image ~.~"
docker build -t $IMAGE_NAME:$IMAGE_TAG .

if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    echo "automate replace"
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi

echo "run front ~.~"
docker run -d \
  -p $HOST_HTTP_PORT:$CONTAINER_HTTP_PORT \
  -p $HOST_HTTPS_PORT:$CONTAINER_HTTPS_PORT \
  -e SERVER_NAME=$SERVER_NAME \
  --name $CONTAINER_NAME $IMAGE_NAME:$IMAGE_TAG

echo "front on ports $HOST_HTTP_PORT and $HOST_HTTPS_PORT"