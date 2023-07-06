FROM jdk8-debian

WORKDIR /home/mall

COPY mall-gateway.jar /home/mall/

VOLUME logs

EXPOSE 10010

ENTRYPOINT ["java", "-jar", "mall-gateway.jar"]
