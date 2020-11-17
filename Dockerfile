FROM adoptopenjdk:11-jre-openj9

ADD build/install/TimeTableBot /TimeTableBot

VOLUME /data

CMD cd /data && chmod +x /TimeTableBot/bin/TimeTableBot && /TimeTableBot/bin/TimeTableBot