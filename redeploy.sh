#!/bin/bash

docker-compose down

docker image prune -a -f

docker-compose up --build -d

docker image prune -f