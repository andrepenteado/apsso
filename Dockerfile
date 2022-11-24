# Stage 1
# Build
FROM maven:3-openjdk-17 AS build-backend
WORKDIR /apsso
COPY . /apsso
RUN mvn -U clean package --file pom.xml -DskipTests

# Stage 2
# Deploy
FROM tomcat:10-jre17
COPY --from=build-backend /apsso/target/apsso.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
