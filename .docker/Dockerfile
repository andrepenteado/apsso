# Stage 1
# Build
FROM maven:3-openjdk-17 AS build
WORKDIR /apsso
COPY .. /apsso
RUN mvn -U clean package --file pom.xml -DskipTests

# Stage 2
# Deploy
FROM tomcat:10-jre17
COPY --from=build /apsso/target/apsso.war /usr/local/tomcat/webapps/ROOT.war
RUN sed -i 's/port="8080"/port="30000"/' ${CATALINA_HOME}/conf/server.xml
