# Index

- [BPMN](bpmn.md)
- [External Services](external-services.md)
  - [Bank](external-services.md#bank)
  - [GIS](external-services.md#gis)
  - [Restaurant](external-services.md#restaurant)
  - [Delivery](external-services.md#delivery)  
<!--
 - [ACMEat agency](acmeat-agency.md)
  - [Web services](acmeat-agency.md#web-services)
  - [BPMS](acmeat-agency.md#bpms)
  - [Frontend](acmeat-agency.md#frontend)
- [Clients](clients.md)
  - [User](clients.md#user)
  - [Restaurant](clients.md#restaurant)
-->
## Development Setup

- Camunda Platform (Tomcat): https://camunda.org/download/
- Camunda Modeler: https://camunda.org/download/modeler/
- Jolie: http://www.jolie-lang.org/downloads.html
- Intellij Idea: https://www.jetbrains.com/idea/download/
- PyCharm: https://www.jetbrains.com/pycharm/download/
- VSCode: https://code.visualstudio.com/download
- Docker: https://www.docker.com/get-started
- Postman: https://www.getpostman.com/downloads/
- SoapUi: https://www.soapui.org/downloads/soapui.html
- Maven: https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/

## Startup Instructions (Linux)

### Requirements
- Docker
- Maven
- Camunda Platform

### Steps
1. Run `git clone https://github.com/AdamF42/acmEat.git`
2. Get an Api key from ` https://www.graphhopper.com/`
3. Sobstitute `API_KEY` in  `PROJ_DIR/gisService/distance.js` with the generated one from step 2.
4. Go into project folder
5. Run `build.sh` 
6. Go into PROJ_DIR/acme-agency
7. Run `mvn install`
8. Copy `PROJ_DIR/acme-agency/acmeat/acmeat.war`, `PROJ_DIR/acme-agency/acmeat-ws/acmeat-ws.war` and `PROJ_DIR/acme-agency/acmeat-frontend/acmeat-frontend.war` into `CamundaPlatform/server/apache-tomcat-9.0.19/webapps`  
9. Edit CamundaPlatform/server/apache-tomcat-9.0.19/conf/context.xml adding  `<Loader delegate="true"/>` inside `<Context>` 
10. Copy `restaurant.json` into `CamundaPlatform/`