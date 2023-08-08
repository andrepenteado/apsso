# Stage 1
# Build
FROM maven:3-openjdk-17 AS build
WORKDIR /apsso/
COPY pom.xml /apsso/
COPY /backend/pom.xml /apsso/backend/
COPY /login/ /apsso/login/
RUN mvn --projects login -U clean package --file /apsso/pom.xml -DskipTests

# Stage 2
# Deploy
FROM tomcat:10-jre17
COPY --from=build /apsso/login/target/login.war /usr/local/tomcat/webapps/ROOT.war
RUN sed -i 's/port="8080"/port="30000"/' ${CATALINA_HOME}/conf/server.xml
