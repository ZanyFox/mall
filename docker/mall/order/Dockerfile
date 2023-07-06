FROM jdk8-debian

WORKDIR /home/mall

COPY mall-order-service.jar /home/mall/

VOLUME logs

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "mall-order-service.jar"]
