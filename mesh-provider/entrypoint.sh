#!/bin/sh -e

echo "The application will start in ${APP_SLEEP}s..." && sleep ${APP_SLEEP}
cp /root/app/spec.yaml /opt/tsf/app_config/
exec java -XX:InitialRAMPercentage=40.0 -XX:MaxRAMPercentage=80.0 -XX:MinRAMPercentage=50.0 ${JAVA_OPTS} \
     	-Djava.security.egd=file:/dev/./urandom \
     	-jar /root/app/app.jar ${RUN_ARGS} "$@"