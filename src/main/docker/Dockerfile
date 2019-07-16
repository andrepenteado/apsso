FROM tomcat:9-jre11

# Timezone
RUN echo "America/Sao_Paulo" > /etc/timezone
RUN ln -sf /usr/share/zoneinfo/Brazil/East /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata

# Add valve for proxy with SSL termination
RUN sed -i 's/unpackWARs="true" autoDeploy="true">/unpackWARs="true" autoDeploy="true"><Valve className="org.apache.catalina.valves.RemoteIpValve" requestAttributesEnabled="true" remoteIpHeader="x-forwarded-for" protocolHeader="x-forwarded-proto"\/>/' /usr/local/tomcat/conf/server.xml

#Deploy App
COPY target/ap-sso.war /usr/local/tomcat/webapps

EXPOSE 8080