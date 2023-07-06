FROM jdk8-debian

WORKDIR /home/mall

COPY mall-cart-service.jar /home/mall/

VOLUME logs

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "mall-cart-service.jar"]
