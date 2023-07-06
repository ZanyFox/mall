FROM jdk8-debian

WORKDIR /home/mall

COPY mall-coupon-service.jar /home/mall/

VOLUME logs

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "mall-coupon-service.jar"]
