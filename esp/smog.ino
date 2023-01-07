#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include "PMS.h"
#include <SoftwareSerial.h>

String smog_id = "2";

const char* ssid = "";
const char* password = "";
const char* serverAddress = "https://192.168.1.106:8443/api/iot/sensor";

SoftwareSerial pmsSerial(D2, D3); //TX, RX
PMS pms(pmsSerial);
PMS::DATA psmData;

void connectToLAN() {
  delay(6000); // Wait for a second
  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  delay(1000); // Wait for two seconds

  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());
}

void setup() {
  delay(5000);
  Serial.begin(115200);
  pmsSerial.begin(9600);
  pms.passiveMode();

  connectToLAN();
}

void loop() {
  Serial.println("Waking up, wait 30 seconds for stable readings...");
  pms.wakeUp();
  delay(30000);

  Serial.println("Send read request...");
  pms.requestRead();

  if(pms.readUntil(psmData)) {
    float pm1 = psmData.PM_AE_UG_1_0;
    float pm25 = psmData.PM_AE_UG_2_5;
    float pm10 = psmData.PM_AE_UG_10_0;
    Serial.print("PM 1.0 (ug/m3): ");
    Serial.println(pm1);

    Serial.print("PM 2.5 (ug/m3): ");
    Serial.println(pm25);

    Serial.print("PM 10.0 (ug/m3): ");
    Serial.println(pm10);

    Serial.println();

    sendHttpRequest(getPMSJson(pm1, pm25, pm10));
  } else {
      Serial.println(F("Failed to read from PMS sensor!"));
  }
  Serial.println("Going to sleep for 20 seconds.");
  pms.sleep();
  delay(25000);
}

void sendHttpRequest(String json) {
  if(WiFi.status()== WL_CONNECTED){
    WiFiClientSecure client;
    client.setInsecure();
    HTTPClient https;

    https.begin(client, serverAddress);

    https.addHeader("Content-Type", "application/json");
    https.addHeader("Content-Length", String(json.length()));

    int httpResponseCode = https.POST(json);

    //Serial.println(httpResponseCode);
    // Free resources
    https.end();
  }
  else {
    Serial.println("WiFi Disconnected");
  }
}

String getPMSJson(float pm1, float pm25, float pm10) {
     String httpRequestData = "{\"id\":" + smog_id +
                               ",\"values\":{\"PM1\":{\"value\":" + String(pm1) +
                                                           ",\"unit\":\"MICROGRAMS_PER_CUBIC_METER\"},"+
                                           "\"PM25\":{\"value\":"+ String(pm25) +
                                                          ",\"unit\":\"MICROGRAMS_PER_CUBIC_METER\"}," +
                                           "\"PM10\":{\"value\":"+ String(pm10) +
                                                          ",\"unit\":\"MICROGRAMS_PER_CUBIC_METER\"}}," +
                                "\"location\":\"Kitchen\","+
                                "\"sensorType\":\"SMOG\"}";
    return httpRequestData;
}

