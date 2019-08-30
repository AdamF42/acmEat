#!/usr/bin/env bash

# Remove all containers and images
[ "$(docker ps -a | grep bank)" ] && docker stop bank 
docker rm bank
docker image rm bank

[ "$(docker ps -a | grep delivery)" ] && docker stop delivery
docker rm delivery
[ "$(docker ps -a | grep delivery2)" ] && docker stop delivery2
docker rm delivery2
docker image rm delivery

[ "$(docker ps -a | grep gis)" ] && docker stop gis
docker rm gis
docker image rm gis

[ "$(docker ps -a | grep restaurant)" ] && docker stop restaurant
docker rm restaurant
[ "$(docker ps -a | grep restaurant2)" ] && docker stop restaurant2
docker rm restaurant2
docker image rm restaurant


