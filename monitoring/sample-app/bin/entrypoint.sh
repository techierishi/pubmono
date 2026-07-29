#!/usr/bin/env bash
set -x

JMX_PORT=9010
JMX_EXPORT_PORT=12345
HOST="0.0.0.0"

nohup java -jar /opt/app/jmxexporter/jmx_prometheus_httpserver-0.20.0.jar $JMX_EXPORT_PORT /opt/app/jmxexporter/config.yaml &

java \
  -Dsun.management.jmxremote.level=FINEST \
  -Dsun.management.jmxremote.handlers=java.util.logging.ConsoleHandler \
  -Djava.util.logging.ConsoleHandler.level=FINEST \
  -Dcom.sun.management.jmxremote.local.only=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.port=$JMX_PORT \
  -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT \
  -Dcom.sun.management.jmxremote.host=$HOST \
  -Djava.rmi.server.hostname=$HOST \
  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
  -jar /opt/app/app.jar

