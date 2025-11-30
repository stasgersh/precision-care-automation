#!/bin/bash
set -e
#gossamer3 login
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 118704808437.dkr.ecr.us-east-1.amazonaws.com
sed -i 's/\r//g' ./entrypoint.sh
docker build -t oncocare-vnv-tests .
docker tag oncocare-vnv-tests:latest 118704808437.dkr.ecr.us-east-1.amazonaws.com/oncocare-vnv-tests:latest
docker image prune -f
docker push 118704808437.dkr.ecr.us-east-1.amazonaws.com/oncocare-vnv-tests:latest