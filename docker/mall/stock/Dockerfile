FROM jdk8-debian

WORKDIR /home/mall

COPY mall-stock-service.jar /home/mall/

VOLUME logs

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "mall-stock-service.jar"]
