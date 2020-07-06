#include <ESP8266WiFi.h>
#include <SPI.h>
#include <MFRC522.h>
#include <Wire.h>
#include<WiFiClient.h>
#include <ESP8266HTTPClient.h>

const char* ssid= "Farwithinia";
const char* pass= "saminadmin";

MFRC522 mfrc522(D4, D3);  // Create MFRC522 instance.
 
void setup() 
{
  Serial.begin(9600);   // Initiate a serial communication

  
//  Serial.begin(115200);
  Serial.println();
  Serial.print("Wifi connecting to: ");
  Serial.print(ssid);

  WiFi.begin(ssid,pass);

  Serial.println();
  Serial.print("Connecting...");

  while(WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");

 
    }
    
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Approximate your card to the reader...");
  Serial.println();

}
void loop() 
{

  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  byte letter;
  
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : ""));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  
  Serial.println();

  HTTPClient http;  
    String Link;
   
  int httpCode=100;
 
  //Post Data
 
  Link = "http://ea4130c6.ngrok.io/smart_id/arduinoAttendance.php?r_tag="+content+"&sec_rfid_reader=375r";
  Serial.println(Link);
  
  http.begin(Link);    
  http.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
  httpCode = http.GET();
  Serial.println(httpCode);
  http.end();

  delay(500);
  
} 
