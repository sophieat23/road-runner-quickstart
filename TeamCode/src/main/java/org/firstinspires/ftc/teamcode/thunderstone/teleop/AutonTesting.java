package org.firstinspires.ftc.teamcode.thunderstone.teleop;// package thunderstone.powerplay.teamcode;


// public class AutonTesting {

//     // todo: write your code here
    
//     boolean config = true;
//         int state = 1;
        
//         boolean config = true;


//         while (config) {
            
//                 switch (state) {
    
//                     case 1: //blue or red
    
//                         telemetry.addData("Select red or blue side. Press X for blue and B for red.", " ");
//                         telemetry.update();
//                         if (gamepad1.x) { //blue
                            
//                             telemetry.addData("Blue side selected. To cancel, press down.", " ");
//                             telemetry.update(); 
//                             if (!gamepad1.dpad_down) {
//                                 state = 2; //continue on with selecting left or right for blue side
//                             } else {
//                                 state = 1; //reselect blue/red side
//                             }
    
//                         } else if (gamepad1.b) { //red
    
//                             telemetry.addData("Red side selected. To cancel, press down.", " ");
//                             telemetry.update();
//                             if (!gamepad1.dpad_down) {
//                                 state = 3; //continue on with selecting left or right for red side
//                             } else {
//                                 state = 1; //reselect blue/red side
//                             }
    
//                         }
//                         break;
    
//                     case 2: //blue side left or right
    
//                         telemetry.addData("Blue side confirmed. Select left or right with the arrow buttons. Note: perspective:looking out onto field", " ");
//                         telemetry.update();
//                         if (gamepad1.dpad_left) {
//                             telemetry.addData("Left side selected (blue). To cancel, press down.", " ");
//                             telemetry.update();
//                             if (!gamepad1.dpad_down) {
//                                 state = 4; //continue on with left blue side
//                             } else {
//                                 state = 2; //reselect left/right blue side
//                             }
//                         } else if (gamepad1.dpad_right) {
//                             telemetry.addData("Right side selected (blue). To cancel, press down.", " ");
//                             telemetry.update();
//                             if (!gamepad1.dpad_down) {
//                                 state = 5; //continue on with right blue side
//                             } else {
//                                 state = 2; //reselect left/right blue side
//                             }
//                         }
//                         break;
    
//                     case 3: //red side left or right
    
//                         telemetry.addData("Red side confirmed. Select left or right with the arrow buttons.", " ");
//                         telemetry.update();
//                         if (gamepad1.dpad_left) {
//                             telemetry.addData("Left side selected (red). To cancel, press down.", " ");
//                             telemetry.update();
//                             if (!gamepad1.dpad_down) {
//                                 state = 6; //continue on with left red side
//                             } else {
//                                 state = 3; //reselect left/right red side
//                             }
//                         } else if (gamepad1.dpad_right) {
//                             telemetry.addData("Right side selected (red). To cancel, press down.", " ");
//                             telemetry.update();
//                             if (!gamepad1.dpad_down) {
//                                 state = 7; //continue on with right red side
//                             } else {
//                                 state = 3; //reselect left/right red side
//                             }
//                         }
//                         break;
    
//                     case 4: //blue side left
//                         telemetry.addData("Blue side left confirmed.", " ");
//                         config = false;
//                         telemetry.update();
//                         break;
    
//                     case 5: //blue side right
//                         telemetry.addData("Blue side right confirmed.", " ");
//                         telemetry.update();
//                         config = false;
//                         break;
    
//                     case 6: //red side left
//                         telemetry.addData("Red side left confirmed.", " ");
//                         telemetry.update();
//                         config = false;
        
//                         break;
    
//                     case 7: //red side right
//                         telemetry.addData("Red side right confirmed.", " ");
//                         telemetry.update();
//                         config = false;
//                         break;

// }