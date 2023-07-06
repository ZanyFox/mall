FROM jdk8-debian

WORKDIR /home/mall

COPY mall-goods-service.jar /home/mall/

VOLUME logs

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "mall-goods-service.jar"]
