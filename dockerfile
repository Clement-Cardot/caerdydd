FROM tomcat:9.0.73-jre11-temurin-jammy

# Configure Tomcat
RUN cp -r webapps.dist/manager webapps/manager
COPY tomcat/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY tomcat/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

# Deploy Back-end
COPY back/target/taf.war /usr/local/tomcat/webapps/taf.war

# Deploy Front-end
RUN mkdir /usr/local/tomcat/webapps/front
COPY front/dist/front/* /usr/local/tomcat/webapps/front/