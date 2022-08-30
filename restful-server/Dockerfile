FROM openjdk:17-alpine
WORKDIR /opt
ADD dist/restful-server-assembly-0.0.1.jar /opt/

RUN mkdir /host
WORKDIR /host

ENTRYPOINT ["java", "-jar", "/opt/restful-server-assembly-0.0.1.jar"]