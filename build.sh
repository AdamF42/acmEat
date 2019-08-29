#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

# Set magic variables for current file & dir
__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
__file="${__dir}/$(basename "${BASH_SOURCE[0]}")"
__base="$(basename ${__file} .sh)"
# __root="$(cd "$(dirname "${__dir}")" && pwd)"

# Set variables for folder services
BANK="${__dir}/bankService"
DELIVERY="${__dir}/deliveryService"
GIS="${__dir}/gisService"
RESTAURANT="${__dir}/restaurantService"
CAMUNDA=""

# Build the conteiners 
# if [[ "$(docker images -q myimage:mytag 2> /dev/null)" == "" ]]; then
   # do something
# fi

cd ${BANK}
docker build --tag=bank .

cd ${DELIVERY}
docker build --tag=delivery .

cd ${GIS}
docker build --tag=gis .

cd ${RESTAURANT}
docker build --tag=restaurant .

# Start all services containers
[ ! "$(docker ps -a | grep bank)" ] && docker run -td -p 10000:8000 -p 10001:8070 --name bank --hostname bank bank

[ ! "$(docker ps -a | grep delivery)" ] && docker run -td -p 10002:8080 --name delivery delivery

[ ! "$(docker ps -a | grep delivery2)" ] && docker run -td -p 10003:8080 --name delivery2 delivery

[ ! "$(docker ps -a | grep gis)" ] && docker run -td -p 10004:7778 --name gis gis

[ ! "$(docker ps -a | grep restaurant)" ] && docker run -d -p 10005:5000 --name restaurant restaurant

[ ! "$(docker ps -a | grep restaurant2)" ] && docker run -d -p 10006:5000 --name restaurant2 restaurant


#  update_container(){
#     if [ ! "$(docker ps -q -f name=$1)" ]; then
#         if [ "$(docker ps -aq -f status=exited -f name=$1)" ]; then
#             # cleanup
#             docker rm $1
#         fi
#         # run your container
#         docker run -td --name $1 my-docker-image
#     fi
#  }

# Start camunda container
#[ ! "$(docker ps -a | grep camunda)" ] && docker run -d -p 8090:8090 --name camunda --hostname camunda --link restaurant --link delivery --link bank --link gis camunda/camunda-bpm-platform:latest
