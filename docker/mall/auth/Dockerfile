FROM jdk8-debian

WORKDIR /home/mall

COPY mall-auth-service.jar /home/mall/

VOLUME logs

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "mall-auth-service.jar"]
