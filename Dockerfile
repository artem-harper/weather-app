FROM tomcat:11-jdk21

WORKDIR /usr/local/tomcat

RUN rm -rf webapps/*

COPY target/weatherApp.war webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]