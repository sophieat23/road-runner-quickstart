//package org.firstinspires.ftc.teamcode.thunderstone.teleop;
//
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//public class RubberBandIntake {
//
//    private CRServo leftServo;
//    private CRServo rightServo;
//
//    //constructor
//    public RubberBandIntake() {
//
//    }
//
//    CRServo getLeftServo() {
//        return leftServo;
//    }
//    CRServo getRightServo() {
//        return rightServo;
//    }
//
//    void setLeftServo(CRServo leftServo) {
//        leftServo = this.leftServo;
//    }
//    void setRightServo(CRServo rightServo) {
//        rightServo = this.rightServo;
//    }
//
//    void turnOffServos(CRServo leftServo, CRServo rightServo) {
//        leftServo.setPower(0);
//        rightServo.setPower(0);
//    }
//
//    void fallenConeLeft(CRServo leftServo, CRServo rightServo, double pow) {
//        if (pow <= 1 && pow > 0) { //pow is only positive because the direction is specified in methods
//            leftServo.setPower(pow); //right
//            rightServo.setPower(pow); //right
//        }
////      leftServo.setPower(-.3);
////      turn it back, optional, have to figure out timing
//    }
//
//    void fallenConeRight(CRServo leftServo, CRServo rightServo, double pow) {
//        if (pow <= 1 && pow > 0) {
//            leftServo.setPower(-pow); //left
//            rightServo.setPower(-pow); //left
//        }
//    }
//
//    void pickUpCone(CRServo leftServo, CRServo rightServo, double pow) { //pulls cone up
//        leftServo.setPower(-pow); //left
//        rightServo.setPower(pow); //right
//    }
//
//    void dropCone(CRServo leftServo, CRServo rightServo, double pow) { //pushes cone down
//        leftServo.setPower(pow); //right
//        rightServo.setPower(-pow); //left
//    }
//
//}