/* -------------------------------------------
 * BluetoothControl
 * 
 * Script for Arduino UNO and the HC-06 Bluetooth module. The well-known library SoftwareSerial
 * is used for the communication with the Bluetooth module. No further hardware except the named
 * module is needed.
 * This code is primary used to receive messages from an Android device and send back a response.
 * 
 *
 * Created:   01.10.2018
 * by:        Tobias Leis
 * Modified:  02.10.2018
 * by:        Tobias Leis
 * 
 * Full tutorial and Android code:
 * LINK
 * ------------------------------------------- */
 
#include <SoftwareSerial.h>
 
#define PIN_RECEIVE 2
#define PIN_SEND 3
#define SPEED_BLUETOOTH 9600
#define SPEED_SERIAL 9600

SoftwareSerial blueSerial(PIN_RECEIVE, PIN_SEND);

void setup() {
  Serial.begin(SPEED_SERIAL);
  Serial.println("Initialized.");
  
  blueSerial.begin(SPEED_BLUETOOTH);
  blueSerial.println("Bluetooth connection successful.");
}
 
void loop() {  
  if (Serial.available())
    blueSerial.write(Serial.read() + "\n");
 
  // Gibt alles aus, was empfangen wird:
  if (blueSerial.available()){
    Serial.write(blueSerial.read());
  }
}
