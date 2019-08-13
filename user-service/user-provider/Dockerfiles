FROM java:8-alpine

VOLUME /data/program

ADD target/*.jar user-service.jar

ENTRYPOINT ["java","-jar","user-service.jar"]