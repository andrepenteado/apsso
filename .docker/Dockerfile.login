# Stage 1
# Build
FROM maven:3-amazoncorretto-21-debian AS build
WORKDIR /apsso/
COPY /backend/pom.xml /apsso/
COPY /backend/controle/pom.xml /apsso/controle/
COPY /backend/services/ /apsso/services/
COPY /backend/login/ /apsso/login/
RUN mvn --projects services,login -U clean package --file /apsso/pom.xml -DskipTests

# Stage 2
# Deploy
FROM tomcat:10-jdk21
COPY --from=build /apsso/login/target/login.war /usr/local/tomcat/webapps/ROOT.war
RUN sed -i 's/port="8080"/port="30000"/' ${CATALINA_HOME}/conf/server.xml
EXPOSE 30000


