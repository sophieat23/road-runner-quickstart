package org.firstinspires.ftc.teamcode.thunderstone.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;


public class Drive {
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor backRight;

    private double BRPower;
    private double BLPower;
    private double FRPower;
    private double FLPower;

//    void stopWheels(double BRPower, double BLPower, double FRPower, double FLPower) {
//        BRPower =
//    }
    public double pow(double base, double power){

        double n = 1;

        for(int i = 0; i < power; i++){
            n *= base;
        }
        if(power == 0){
            n = 1; //already assigned this, redundant
        }
        return n;
    }

    void drivingMotion(double BRPower, double BLPower, double FRPower, double FLPower) {


//        if (direction.equals("forward"))


    }
    //     y = gamepad1.left_stick_y ; // *slow
    //     x =  gamepad1.left_stick_x; //*slow

    //     equationY = .75 * pow(y, 3) + .25 * y;
    //     equationX = .75 * pow(x, 3) + .25 * x;

    //   //forward/backward
    //     BLPower += -equationY * slow;
    //     BRPower += -equationY * slow;
    //     FLPower += -equationY * slow;
    //     FRPower += -equationY * slow;

    //     //strafing:
    //     FLPower += equationX * slow;
    //     BLPower += -equationX * slow;
    //     FRPower += -equationX * slow;
    //     BRPower += equationX * slow;


    //     //rotating w/o slow mode
    //     FLPower += (gamepad1.right_stick_x)  * .5; //.5 * slow
    //     BLPower += (gamepad1.right_stick_x)  * .5;
    //     FRPower += (-gamepad1.right_stick_x)  * .5;
    //     BRPower += (-gamepad1.right_stick_x)  * .5;
}