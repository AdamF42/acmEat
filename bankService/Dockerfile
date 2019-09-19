FROM ubuntu:18.04

# install java8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get install -y wget && \
    apt-get install -y unzip && \
    apt-get install -y expect && \
    apt-get -y clean;

# Download and install maven. We need it to run bank rest service.
RUN apt-get install -y maven 

# Application placed into /opt/app
RUN mkdir -p /opt/app
WORKDIR /opt/app

# Selectively add the POM file and install dependencies
COPY pom.xml /opt/app/
RUN mvn install

# rest of the project
COPY src /opt/app/src
RUN mvn package

# Download and install Jolie. We need it to run bank soap service.
RUN wget https://github.com/jolie/website/raw/master/www/files/releases/jolie-1.8.2.jar
COPY ./docker/install_jolie install_jolie
RUN chmod +x /opt/app/install_jolie
RUN /opt/app/install_jolie
ENV JOLIE_HOME /usr/lib/jolie

# expose ports
EXPOSE 8070 8000

# prepare script to start services
COPY ./docker/soap_service soap_service
COPY ./docker/rest_service rest_service
COPY ./docker/start_services.sh start_services.sh
RUN chmod +x /opt/app/start_services.sh
RUN chmod +x /opt/app/soap_service
RUN chmod +x /opt/app/rest_service

# execute it
CMD ./start_services.sh

