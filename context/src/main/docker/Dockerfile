FROM registry.cn-hangzhou.aliyuncs.com/prod-env/jdk1.8:v1
MAINTAINER ZQKHYL
COPY microservice-file.jar /opt/apps/app.jar
COPY start.sh /opt/script/start.sh
RUN chmod +x /opt/script/start.sh
ENV minx 256m
ENV max 1024m
ENV envs prod
ENV port 7002
EXPOSE 7002
CMD ["/opt/script/start.sh"]
