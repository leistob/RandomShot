/* -------------------------------------------
 * MotorControl
 * 
 * Script for Arduino UNO and a unipolar stepper motor (in this case the Sunfounder 28BYJ-48). 
 * The stepper motor is driven by a stepper motor driver board (also from Sunfounder).
 * The code is used to test out the stepper motor and its capabilities (speed, etc).
 * 
 *
 * Created:   03.10.2018
 * by:        Tobias Leis
 * Modified:  03.10.2018
 * by:        Tobias Leis
 * 
 * Full tutorial and Android code:
 * LINK
 * ------------------------------------------- */



#include <Stepper.h>

//the steps of a circle
#define STEPS 80

//set steps and the connection with MCU
Stepper stepper(STEPS, 2, 3, 4, 5);

void setup() {
  stepper.setSpeed(350);
}

void loop() {  
  stepper.step(400);  
}
