package org.firstinspires.ftc.teamcode.thunderstone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "November Practice Testing", group = "6010 TeleOps")

public class NovemberPractice extends LinearOpMode {

    double slow = .5;
    double y = 0;
    double equationY = 0;
    double x = 0;
    double equationX = 0;

    private CRServo leftServo;
    private CRServo rightServo;

    private DcMotorEx backRight;
    private DcMotorEx backLeft;
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;

    private DcMotorEx linearSlide;

    private double BRMotor;
    private double BLMotor;
    private double FRMotor;
    private double FLMotor;

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


    @Override
    public void runOpMode() {

        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");

        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");

        linearSlide = hardwareMap.get(DcMotorEx.class, "linearSlide");
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        BLMotor = 0;
        BRMotor = 0;
        FLMotor = 0;
        FRMotor = 0;

        telemetry.addData("left Servo Pos", leftServo.getPower());
        telemetry.addData("right Servo Pos", rightServo.getPower());


        telemetry.update();


        waitForStart();

        telemetry.addData("left Servo Pos", leftServo.getPower());
        telemetry.addData("right Servo Pos", rightServo.getPower());

        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {

                BLMotor = 0;
                BRMotor = 0;
                FLMotor = 0;
                FRMotor = 0;


                if(gamepad1.b){
                    rightServo.setPower(1);
                } else if(gamepad1.x){
                    rightServo.setPower(-1);

                } else if(gamepad1.dpad_down){
                    rightServo.setPower(1);

                }else if(gamepad1.dpad_up){
                    rightServo.setPower(-1);

                } else if(gamepad1.y){
                    rightServo.setPower(-1);
                } else{
                    rightServo.setPower(0);
                }

                if(gamepad1.dpad_left){
                    leftServo.setPower(1);
                } else if(gamepad1.dpad_right){
                    leftServo.setPower(-1);

                } else if(gamepad1.dpad_down){
                    leftServo.setPower(-1);

                }else if(gamepad1.dpad_up){
                    leftServo.setPower(1);

                } else if(gamepad1.y){
                    leftServo.setPower(-1);
                } else {
                    leftServo.setPower(0);
                }

                y = gamepad1.left_stick_y ; // *slow
                x =  gamepad1.left_stick_x; //*slow

                equationY = .75 * pow(y, 3) + .25 * y;
                equationX = .75 * pow(x, 3) + .25 * x;

                //forward/backward
                BLMotor += -equationY * slow;
                BRMotor += -equationY * slow;
                FLMotor += -equationY * slow;
                FRMotor += -equationY * slow;

                //strafing:
                FLMotor += equationX * slow;
                BLMotor += -equationX * slow;
                FRMotor += -equationX * slow;
                BRMotor += equationX * slow;


                //rotating w/o slow mode
                FLMotor += (gamepad1.right_stick_x)  * .5; //.5 * slow
                BLMotor += (gamepad1.right_stick_x)  * .5;
                FRMotor += (-gamepad1.right_stick_x)  * .5;
                BRMotor += (-gamepad1.right_stick_x)  * .5;

                backLeft.setPower(BLMotor);
                backRight.setPower(BRMotor);
                frontLeft.setPower(FLMotor);
                frontRight.setPower(FRMotor);



                //linear slide
                if(gamepad1.left_bumper) {
                    linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    linearSlide.setPower(.5);
                    telemetry.addData("Outake Pow", linearSlide.getPower());


                } else if(gamepad1.right_bumper){
                    linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    linearSlide.setPower(-.1);
                    telemetry.addData("Outake Pow", linearSlide.getPower());

                } else {
                    linearSlide.setPower(0);
                }


                telemetry.addData("left Servo Pos", leftServo.getPower());
                telemetry.addData("right Servo Pos", rightServo.getPower());

                telemetry.update();


            }




        }


    }


}
