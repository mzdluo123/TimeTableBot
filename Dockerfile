FROM adoptopenjdk:11-jre-openj9
WORKDIR /app
COPY TimeTableBot .
VOLUME /data
RUN chmod +x /app/bin/TimeTableBot
CMD cd /data && /app/bin/TimeTableBot