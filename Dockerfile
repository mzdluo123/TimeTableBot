FROM openjdk:11

COPY . code

RUN cd code && chmod +x gradlew && ./gradlew installDist

COPY code/build/install/TimeTableBot /TimeTableBot

RUN rm -rf /tmp/*

VOLUME data

CMD chmod +x /TimeTableBot/bin/TimeTableBot && /TimeTableBot/bin/TimeTableBot