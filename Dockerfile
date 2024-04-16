FROM tomcat:jdk21-temurin

MAINTAINER rymcu.com

RUN mkdir -p /logs/tenon

RUN rm -rf /usr/local/tomcat/webapps.dist

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/tenon.war /usr/local/tomcat/webapps/

EXPOSE 8080
