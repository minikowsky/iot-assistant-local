#!/bin/bash
echo 'Stopping the application service'
systemctl stop iot-assistant.service

if [ -e /iot-assistant-rest/iot-assistant-rest.jar ]
then
  echo 'Removing old jar'
  rm /iot-assistant-rest/iot-assistant-rest.jar
fi
echo 'Moving new jar to application directory'
mv /home/pi/iot-assistant-rest.jar /iot-assistant-rest
echo 'Starting the application service'
systemctl start iot-assistant.service