./gradlew -DsocksProxyHost=localhost -DsocksProxyPort=12121 installDist
mv build/install/TimeTableBot .
sudo docker build . --tag timetablebot
rm -rf TimeTableBot