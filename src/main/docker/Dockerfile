FROM tomcat:10-jre17

# Timezone
RUN echo "America/Sao_Paulo" > /etc/timezone              && \
    apt-get update                                        && \
    apt-get install -y tzdata                             && \
    rm /etc/localtime                                     && \
    ln -sf /usr/share/zoneinfo/Brazil/East /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata             && \
    apt-get clean

#Deploy App
COPY target/apsso.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
