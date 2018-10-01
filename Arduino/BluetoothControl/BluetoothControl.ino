/* -------------------------------------------
/* Bluetooth Seriell senden und empfangen
/* www.frag-duino.de
/* -------------------------------------------
/* Befehle:
/* Sendet alles, was eingegeben wird
/* Empfaengt alles, was gesendet wurde
 ------------------------------------------- */
 
// Konstanten
#define PIN_RECEIVE 2
#define PIN_SEND 3
#define PIN_STATUS 13
#define SPEED_BLUETOOTH 9600
#define SPEED_SERIAL 9600
 
// INEX BT Board 9600
// Linkmatic 115200
 
// Includes
#include <SoftwareSerial.h>
 
// Variablen
SoftwareSerial blueSerial(PIN_RECEIVE, PIN_SEND);
int zustand_status = HIGH;
 
void setup() {
  Serial.begin(SPEED_SERIAL);
  Serial.println("Bluetooth Serial initialisiert");
  pinMode(PIN_STATUS, OUTPUT);
 
  // auf seriellen Port horchen
  blueSerial.begin(SPEED_BLUETOOTH);
  blueSerial.println("Bluetooth Serial Verbindung hergestellt");
}
 
void loop() {
  // Sendet alles, was eingegeben wird.
  if (Serial.available())
    blueSerial.write(Serial.read());
 
  // Gibt alles aus, was empfangen wird:
  if (blueSerial.available()){
    Serial.write(blueSerial.read());
  }
}
