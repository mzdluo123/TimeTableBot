FROM adoptopenjdk:11-jre-openj9
WORKDIR /app
COPY TimeTableBot .
VOLUME /data
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
RUN chmod +x /app/bin/TimeTableBot
CMD cd /data && /app/bin/TimeTableBot