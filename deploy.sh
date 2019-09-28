#!/usr/bin/env bash

rm -r /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps/acmeat-frontend 

rm -r /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps/acmeat-ws

rm -r /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps/acmeat

rm /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps/acmeat-frontend.war 

rm /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps/acmeat-ws.war

rm /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps/acmeat.war



cp /home/adamf42/Projects/ACMEat/acme-agency/acmeat-frontend/target/acmeat-frontend.war /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps


cp /home/adamf42/Projects/ACMEat/acme-agency/acmeat-ws/target/acmeat-ws.war /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps


cp /home/adamf42/Projects/ACMEat/acme-agency/acmeat/target/acmeat.war /home/adamf42/Projects/CamundaPlatformEE/server/apache-tomcat-9.0.19/webapps
