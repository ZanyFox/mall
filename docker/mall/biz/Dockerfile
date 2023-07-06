FROM jdk8-debian

WORKDIR /home/mall

COPY mall-biz-service.jar /home/mall/

VOLUME logs

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "mall-biz-service.jar"]
