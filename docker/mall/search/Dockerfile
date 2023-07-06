FROM jdk8-debian

WORKDIR /home/mall

COPY mall-search-service.jar /home/mall/

VOLUME logs

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "mall-search-service.jar"]
