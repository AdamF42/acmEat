# Index

* [BPMN](bpmn.md)
* [External Services](external-services.md)
  + [Bank](external-services.md#bank)
  + [GIS](external-services.md#gis)
  + [Restaurant](external-services.md#restaurant)
  + [Delivery](external-services.md#delivery)  
* [ACMEat](acme-agency.md)
    - [BPMS](acme-agency.md#bpms)
    - [WebServices](acme-agency.md#webservices)
    - [Frontend](acme-agency.md#frontend)
    - [Common](acme-agency.md#common)

## Development Setup

* Camunda Platform (Tomcat): https://camunda.org/download/
* Camunda Modeler: https://camunda.org/download/modeler/
* Jolie: http://www.jolie-lang.org/downloads.html
* Intellij Idea: https://www.jetbrains.com/idea/download/
* PyCharm: https://www.jetbrains.com/pycharm/download/
* VSCode: https://code.visualstudio.com/download
* Docker: https://www.docker.com/get-started
* Postman: https://www.getpostman.com/downloads/
* SoapUi: https://www.soapui.org/downloads/soapui.html
* Maven: https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/

## Startup Instructions (Linux)

### Requirements

* Docker
* Maven
* Camunda Platform

### Steps

01. Run `git clone https://github.com/AdamF42/acmEat.git` 
02. Get an Api key from `https://www.graphhopper.com/` 
03. Sobstitute `API_KEY` in `PROJ_DIR/gisService/distance.js` with the generated one from step 2.
04. Go into project folder
05. Run `build.sh` 
06. Go into `PROJ_DIR/acme-agency`
07. Run `mvn install` 
08. Copy `PROJ_DIR/acme-agency/acmeat/acmeat.war` , `PROJ_DIR/acme-agency/acmeat-ws/acmeat-ws.war` and `PROJ_DIR/acme-agency/acmeat-frontend/acmeat-frontend.war` into `CamundaPlatform/server/apache-tomcat-9.0.19/webapps` 
09. Edit `CamundaPlatform/server/apache-tomcat-9.0.19/conf/context.xml` adding `<Loader delegate="true"/>` inside `<Context>` 
10. Copy `restaurant.json` into `CamundaPlatform/` 

