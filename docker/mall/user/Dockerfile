FROM jdk8-debian

WORKDIR /home/mall

COPY mall-user-service.jar /home/mall/

VOLUME logs

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "mall-user-service.jar"]
