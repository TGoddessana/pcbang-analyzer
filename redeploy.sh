#!/bin/bash

# Stop and remove existing containers
docker-compose down

# Remove unused images
docker image prune -a -f

# Pull the latest code from git
git pull origin main

# Build and start new containers
docker-compose up --build -d

# Remove dangling images
docker image prune -f