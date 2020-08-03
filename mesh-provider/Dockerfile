FROM openjdk:11.0-slim

LABEL description="your description"
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    APP_SLEEP=0 \
    JAVA_OPTS="" \
    RUN_ARGS="" \
    APP_NAME_ID=""

RUN ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata

ENV TZ "Asia/Shanghai"

RUN mkdir -p /root/app/logs/
RUN mkdir -p /opt/tsf/app_config/

WORKDIR /root/app/
COPY entrypoint.sh \
     spec.yaml \
     target/app.jar \
     /root/app/

RUN chmod 755 entrypoint.sh 

ENTRYPOINT ["./entrypoint.sh"]
EXPOSE 18081
