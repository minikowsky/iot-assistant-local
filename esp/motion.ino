#include <WiFi.h>
#include <HTTPClient.h>

String id = "1";

const char* ssid = "";
const char* password = "";

const char* serverAddress = "https://192.168.1.106:8443/api/iot/detector";

const int PIN_TO_SENSOR = 15; // GIOP19 pin connected to OUTPUT pin of sensor
int pinStateCurrent   = LOW;  // current state of pin
int pinStatePrevious  = LOW;  // previous state of pin

void connectToLAN() {
  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());

  Serial.println("Timer set to 5 seconds (timerDelay variable), it will take 5 seconds before publishing the first reading.");
}

void setup() {
  Serial.begin(9600);            // initialize serial
  pinMode(PIN_TO_SENSOR, INPUT); // set ESP32 pin to input mode to read value from OUTPUT pin of sensor

  connectToLAN();
}

void loop() {
  pinStatePrevious = pinStateCurrent; // store old state
  pinStateCurrent = digitalRead(PIN_TO_SENSOR);   // read new state

  if (pinStatePrevious == LOW && pinStateCurrent == HIGH) {   // pin state change: LOW -> HIGH
    Serial.println("Motion detected!");
    sendHttpRequest(getMotionJson());
    delay(10000);
  }
  else
  if (pinStatePrevious == HIGH && pinStateCurrent == LOW) {   // pin state change: HIGH -> LOW
    Serial.println("Motion stopped!");
  }
}

void sendHttpRequest(String json) {
  if(WiFi.status()== WL_CONNECTED){
    WiFiClientSecure client;
    client.setInsecure();
    HTTPClient https;

    https.begin(client, serverAddress);

    https.addHeader("Content-Type", "application/json");


    int httpResponseCode = https.POST(json);

    Serial.println(httpResponseCode);
    // Free resources
    https.end();
  }
  else {
    Serial.println("WiFi Disconnected");
  }
}


String getMotionJson() {
     String httpRequestData = "{\"id\":" + id + ","
                                "\"location\":\"Living room\","+
                                "\"detectorType\":\"MOTION\"}";
    return httpRequestData;
}