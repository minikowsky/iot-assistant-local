#include "DHT.h"
#include <WiFi.h>
#include <HTTPClient.h>
#include <WiFiClientSecure.h>

#define DHTPIN 15
#define DHTTYPE DHT11

String id = "1";

const char* ssid = "";
const char* password = "";

//const char* serverAddress = "http://192.168.0.99:8080/api/iot/sensor";
const char* serverAddress = "https://192.168.1.106:8443/api/iot/sensor";

DHT dht(DHTPIN, DHTTYPE);

void connectToLAN() {
  //WiFi.mode(WIFI_STA);
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
  Serial.begin(9600);

  dht.begin();

  connectToLAN();
}

void loop() {
  // Wait a minute between measurements.
  delay(10000);

  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  Serial.print(F("Humidity: "));
  Serial.print(h);
  Serial.print(F("%  Temperature: "));
  Serial.print(t);
  Serial.print(F("°C "));

  sendHttpRequest(getDHTJson(t,h));

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

    Serial.println(httpResponseCode);

    // Free resources
    https.end();
  }
  else {
    Serial.println("WiFi Disconnected");
  }
}

String getDHTJson(float t, float h) {
     String httpRequestData = "{\"id\":" + id +
                               ",\"values\":{\"TEMPERATURE\":{\"value\":" + String(t) +
                                                           ",\"unit\":\"CELSIUS\"},"+
                                           "\"HUMIDITY\":{\"value\":"+ String(h) +
                                                          ",\"unit\":\"PERCENT\"}}," +
                                "\"location\":\"Living room\","+
                                "\"sensorType\":\"TEMPERATURE_HUMIDITY\"}";
    return httpRequestData;
}

