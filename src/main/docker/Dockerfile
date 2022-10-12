FROM tomcat:9-jre17

# Timezone
RUN echo "America/Sao_Paulo" > /etc/timezone              && \
    apt-get update                                        && \
    apt-get install -y tzdata                             && \
    rm /etc/localtime                                     && \
    ln -sf /usr/share/zoneinfo/Brazil/East /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata             && \
    apt-get clean

# Add valve for proxy with SSL termination
RUN sed -i 's/unpackWARs="true" autoDeploy="true">/unpackWARs="true" autoDeploy="true"><Valve className="org.apache.catalina.valves.RemoteIpValve" requestAttributesEnabled="true" remoteIpHeader="x-forwarded-for" protocolHeader="x-forwarded-proto"\/>/' /usr/local/tomcat/conf/server.xml

# Enable clustering
RUN sed -i 's/<Engine name="Catalina" defaultHost="localhost">/<Engine name="Catalina" defaultHost="localhost"><Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster"\/>/' /usr/local/tomcat/conf/server.xml

#Deploy App
COPY target/ap-sso.war /usr/local/tomcat/webapps

EXPOSE 8080
