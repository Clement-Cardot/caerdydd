FROM tomcat:9.0.73-jre11-temurin-jammy

# Deploy Back-end
COPY back/target/taf.war /usr/local/tomcat/webapps/taf.war

# Deploy Front-end
RUN mkdir /usr/local/tomcat/webapps/front
COPY front/dist/front/* /usr/local/tomcat/webapps/front/