Write-Output("Clean build dir")
./gradlew clean
Write-Output("Build jar")
./gradlew build
Write-Output("Copy files to host")
cp '.\build\libs\iot-assistant-rest.jar' '.\install\'
scp '.\install\*' pi@192.168.0.99:~
Write-Output("Execute installation script")
ssh -t pi@192.168.0.99 'sudo bash install.sh'
Write-Output("Installation finished")