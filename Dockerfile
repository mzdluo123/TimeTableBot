FROM openjdk:11

COPY . /tmp

RUN cd /tmp && chmod +x gradlew && ./gradlew installDist

COPY /tmp/build/install/TimeTableBot /TimeTableBot

RUN rm -rf /tmp/*

VOLUME data

CMD chmod +x /TimeTableBot/bin/TimeTableBot && /TimeTableBot/bin/TimeTableBot