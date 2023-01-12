#!/bin/bash
echo 'Verify environment'
if [ -e /etc/systemd/system/iot-assistant.service ]
then
  echo 'Stopping the application service'
  systemctl stop iot-assistant.service
  echo 'Iot-assistant daemon has been found'
  rm /etc/systemd/system/iot-assistant.service
fi

echo 'Move new daemon to final directory'
mv /home/pi/iot-assistant.service /etc/systemd/system

echo 'Reload daemon'
systemctl daemon-reload

if [ -e /iot-assistant-rest/iot-assistant-rest.jar ]
then
  echo 'Remove old jar'
  rm /iot-assistant-rest/iot-assistant-rest.jar
fi

echo 'Move new jar to application directory'
mv /home/pi/iot-assistant-rest.jar /iot-assistant-rest

echo 'Start the application service'
systemctl start iot-assistant.service