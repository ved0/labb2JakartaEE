FROM bitnami/wildfly:latest
LABEL task="Laboration 3" \
      author="Vedad" \
      description="Creating a Docker image"
COPY target/myapp.war /opt/bitnami/wildfly/standalone/deployments/
EXPOSE 8080
ENV WILDFLY_USERNAME=admin
ENV WILDFLY_PASSWORD=mySecurePassword
ENV WILDFLY_NODE_IDENTIFIER=myServer_!
ENV WILDFLY_RESTEASY_PREFER_JACKSON=false
CMD ["/opt/bitnami/scripts/wildfly/run.sh"]
