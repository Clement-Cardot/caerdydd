# Getting Started

## Prepare Development Environment

1. Install JDK 11 : [(jdk 11.0.18+10)](https://www.azul.com/downloads/?version=java-11-lts&os=windows&package=jdk#zulu)

2. Install Maven : [Maven](https://maven.apache.org/download.cgi)
    - Download Binary (zip for Win tar.gz for Mac)
    - Extract the file in your dev env
    - Add the bin path to your $PATH

<br>

3. Install Node.js : [(LTS : v18.15.0)](https://nodejs.org/download/release/latest-v18.x/)

    - Install the Angular CLI :

        - **FOR WINDOWS ONLY**
            >- Start Powershell **with Admin Rights**
            >- Run this : 
            >```powershell
            > Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
            >```
            >- Enter 'O' and tap Enter key


        - Start Terminal and run this :

        ```sh
        npm install -g @angular/cli
        ```

4. Install Docker : [Docker](https://www.docker.com)

5. Configure VS Code
    - Add extensions :
        - Sonar Lint
        - Docker
        - GitLens
        - Maven

## **Test Environment**
To verify if everything is correctly setup you just have to run the build.sh script

**To run it you can use :**
- Git bash on Windows
- standard terminal on Linux/Mac

# Tools Used
### Back-end :
- AZUL Zulu 11.62.17 (jdk 11.0.18+10)
- SpringBoot 2.7.9
- Maven 3.9.0

### Font :
- Node.js v18.15.0
- Angular

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)