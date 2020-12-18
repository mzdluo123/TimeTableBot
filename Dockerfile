FROM adoptopenjdk:11-jre-openj9
WORKDIR .
COPY build/install/TimeTableBot /TimeTableBot
VOLUME /data
RUN chmod +x /TimeTableBot/bin/TimeTableBot
CMD cd /data && /TimeTableBot/bin/TimeTableBot