# 基础镜像使用java
# FROM openjdk:8-jdk-alpine
FROM adoptopenjdk/openjdk11-openj9

WORKDIR /tmp/log
WORKDIR /tmp/pic
WORKDIR /app

ARG JAR_FILE=manage.jar
ARG TIME_ZONE=Asia/Shanghai

# 添加jar进入容器
ADD ${JAR_FILE} app.jar

ENV JVM_XMS="256m" \
    JVM_XMX="256m" \
    JVM_OPTS="" \
    TZ=${TIME_ZONE}

# 运行jar包
ENTRYPOINT java -Xms${JVM_XMS} -Xmx${JVM_XMX} ${JVM_OPTS} -server -jar app.jar

# 暴露端口
EXPOSE 7070