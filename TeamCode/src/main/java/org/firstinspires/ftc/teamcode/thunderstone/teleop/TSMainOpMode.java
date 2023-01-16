package org.firstinspires.ftc.teamcode.thunderstone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
//do we need an import for the gamepad?

@TeleOp(name = "6010 PowerPlay TeleOp", group = "6010 TeleOps")
public class TSMainOpMode extends LinearOpMode {
    private double slow = 1; //when slow = 1 there is no slow
    private double slowR = .5; //slow for rotating
    private double y = 0;
    private double equationY = 0;
    private double x = 0;
    private double equationX = 0;

    private double strafePow = 0;
    //    private LinearSlide linearSlide = new LinearSlide();
    private Drive drive = new Drive();
    private CRServo leftServo;
    private CRServo rightServo;
    private DcMotorEx lift;
//    private DcMotorEx lsMotor = linearSlide.getLsMotor();

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    private double BRMotor;
    private double BLMotor;
    private double FRMotor;
    private double FLMotor;

    private double DR4BMotor;

    public void runOpMode() {
//    public void init() {
//        linear slide
//        lsMotor = hardwareMap.get(DcMotorEx.class, "linearSlide");
//        note: not using linear slide right now
/*
        intake servos
        note: currently treating servos as instance variables within this class,
        maybe should try treating them as instance data in the RubberBandIntake class-->
        would have to change methods
 */

        //ATTENTION!!!
        //wrong sides rn bc auton code is reversed, need to fix once thats addressed!!
        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");

        //lift:
        lift = hardwareMap.get(DcMotorEx.class, "DR4B");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentH= lift.getCurrentPosition();
        // int previousH = lift.getCurrentPosition();
        lift.setTargetPosition(currentH);

        boolean wasHigh;
        boolean wasLow;

        //wheels
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");

        BLMotor = 0;
        BRMotor = 0;
        FLMotor = 0;
        FRMotor = 0;

        SampleMecanumDrive myLocalizer = new SampleMecanumDrive(hardwareMap);
//    }
//    public void loop() {

/*
        lsMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        lsMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

 */
        //servos are in reverse direction when configuration has correct left/right
        //for now its forward bc direction is reversed in auton
        //update idk if that actually works
        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);

        lift.setDirection(DcMotorEx.Direction.REVERSE);


        // leftServo.setDirection().FORWARD;
        // rightServo.setDirection().REVERSE;

        waitForStart();

        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {

                BLMotor = 0;
                BRMotor = 0;
                FLMotor = 0;
                FRMotor = 0;
                DR4BMotor = 0;

                //slow:
//                if(gamepad1.y){ //has this even been working?
//                    slow = .25;
//                    slowR = .5;
//                } else {
//                    slow = .5;
//                    slowR = 1;
//                }

                /*
                gamepad input
                 */

//                if (gamepad1.dpad_up) {
//                    lsMotor.setPower(.3); //arbitrary power val, just to test linear slide
//                }
                /*

                servo buttons (OLD SERVO CODE SOMETHING DOESNT WORK NEED TO FIGURE OUT)
                 */
                // if (gamepad1.dpad_up) {
                //     intake.pickUpCone(leftServo, rightServo, 1);
                // }

                // if (gamepad1.dpad_down) {
                //     intake.dropCone(leftServo, rightServo, 1);
                // }

                //temp servo code:
                if (gamepad1.dpad_up) { //intake cone
                    leftServo.setPower(1);
                    rightServo.setPower(-1);
                } else if (gamepad1.dpad_down) { //drop cone
                    leftServo.setPower(-1);
                    rightServo.setPower(1);
                } else {
                    leftServo.setPower(0);
                    rightServo.setPower(0);
                }

                //   //TEMPORARY SERVO CODE UNTIL CLASSES ARE FIXED

                //   //right servo
                //   if(gamepad1.dpad_down){
                //         rightServo.setPower(1);

                //     }else if(gamepad1.dpad_up){
                //         rightServo.setPower(-1);

                //     } else {
                //         rightServo.setPower(0);
                //     }

                //     //leftservo
                //     if(gamepad1.dpad_down){
                //         leftServo.setPower(-1);

                //     }else if(gamepad1.dpad_up){
                //         leftServo.setPower(1);

                //     } else {
                //         leftServo.setPower(0);
                //     }


                /* spinning left
                try to make it spin to pick up a cone, lying w/ base on the right
                right intake spins left
                left intake spins left
                remember: right button for when the cone base is on the right
                change this^
                 */
                // if (gamepad1.x) {
                //     intake.fallenConeLeft(leftServo, rightServo, 1);
                // }
                /* spinning right
                try to make it spin to pick up a cone, lying w/ base on the left
                right intake spins right
                left intake spins right
                remember: left button for when the cone base is on the left
                change this^
                 */
                // if (gamepad1.b) {
                //     intake.fallenConeRight(leftServo, rightServo, 1);
                // }

                // else if{
                //     intake.turnOffServos(leftServo, rightServo); //sets pow to zero
                // }

                /*
                joystick controls
                 */
                y = gamepad1.left_stick_y ; // *slow
                x =  gamepad1.left_stick_x; //*slow

                equationY = .75 * drive.pow(y, 3) + .25 * y;
                equationX = .75 * drive.pow(x, 3) + .25 * x;

                //forward/backward
                BLMotor += -equationY * slow;
                BRMotor += -equationY * slow;
                FLMotor += -equationY * slow;
                FRMotor += -equationY * slow;

                //strafing w/ joystick
                FLMotor += equationX ;//*slow
                BLMotor -= equationX;
                FRMotor -= equationX;
                BRMotor += equationX ;

                //strafing w/ left/right
                strafePow = .7;
                //add imu to make strafing exact
                if (gamepad1.dpad_right) {
                    FLMotor = strafePow ;//*slow
                    BLMotor = -strafePow ;
                    FRMotor = -strafePow ;
                    BRMotor = strafePow ;
                }
                if (gamepad1.dpad_left) {
                    FLMotor = -strafePow ;//*slow
                    BLMotor = strafePow ;
                    FRMotor = strafePow ;
                    BRMotor = -strafePow ;
                }

                //rotating w/o slow mode
                FLMotor += (gamepad1.right_stick_x)  * slowR;
                BLMotor += (gamepad1.right_stick_x)  * slowR;
                FRMotor += (-gamepad1.right_stick_x)  * slowR;
                BRMotor += (-gamepad1.right_stick_x)  * slowR;

                backLeft.setPower(BLMotor);
                backRight.setPower(BRMotor);
                frontLeft.setPower(FLMotor);
                frontRight.setPower(FRMotor);

                //rotating 90 degrees
                //need to test this!
                if (gamepad1.left_bumper) { //rotate to the left 90
                    //we may need to switch to using encoder to do this, or find a way to use roadrunner
                    myLocalizer.turn(Math.toRadians(-90));
                } else if (gamepad1.right_bumper) { //rotate to the right 90
                    myLocalizer.turn(Math.toRadians(90));
                }

                //checking height
                wasHigh = lift.getCurrentPosition() >= 700;
                wasLow = lift.getCurrentPosition() >= 400;

                //lift:
                if(gamepad1.left_trigger>0 && currentH<1000) { //up (and h<1000 is the max height)
                    lift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                    DR4BMotor = gamepad1.left_trigger*1200;
                    lift.setVelocity(DR4BMotor);

                    //for some reason NEED this line of code to work:
                    //work what? - sophie
                    telemetry.addData("Outake Pow", lift.getPower());

                    currentH= lift.getCurrentPosition();
                    // previousH = currentH;
                    // fightGravity = .5;


                } else if(gamepad1.right_trigger>.01 && lift.getCurrentPosition() >30){//down
                     lift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); //does this change anything?
                    currentH= lift.getCurrentPosition();

                    if(currentH > 300){
                        lift.setVelocity(-800);
                        // telemetry.addData("lift velocity", lift.getVelocity());
//                        lift.setPower(-.0000000000000001);//gravity is doing most of the work so only need a little power to keep it from falling down too fast
                    }
//                    else if (currentH > 150){ //removed else if current H > 150
//                        lift.setPower(.001); //making it power down
//                        // telemetry.addData("lift velocity", lift.getVelocity());
//                        // lift.setPower(.0005);//need less power because doesn't have as much gravitational potential energy
//                    }
                    else{
                        lift.setVelocity(-600);
//                        lift.setPower(.0001);//gravity doesnt do as much work here so power needs to be set to negative
                    }

                } else if (gamepad1.x) { //high junction preset
                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    currentH = 800;
                    lift.setTargetPosition(currentH);
                    lift.setPower(.5);
                    //lift.setVelocity();

                    telemetry.addData("lift velocity", lift.getVelocity());

                } else if (gamepad1.y) { //mid junction preset
                    currentH = 500; //might need to change this, temp val
                    lift.setTargetPosition(currentH);
                    lift.setPower(.5);
                    //lift.setVelocity();

                    telemetry.addData("lift velocity", lift.getVelocity());
                } else if (gamepad1.b){ //low junction preset
                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    currentH = 300;
                    lift.setTargetPosition(currentH);
                    lift.setPower(.5);
                    //lift.setVelocity();

                    telemetry.addData("lift velocity", lift.getVelocity());

                } else if(gamepad1.a){ //lowest preset for picking up cones
                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    currentH = 70;
                    lift.setTargetPosition(currentH);
                    if (wasHigh) {
                        lift.setVelocity(800);
//                        lift.setPower(.3);
                    } else if (wasLow) {
                        lift.setVelocity(700);
//                        lift.setPower(.4);
                    } else {
                        lift.setVelocity(600);
//                        lift.setPower(.5);
                    }

                    telemetry.addData("lift velocity", lift.getVelocity());

                } else {

                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    lift.setTargetPosition(currentH);

                }

                //getting direction
                //this doesnt work, is always false
//                if (lift.getDirection().equals("REVERSE")) {
//                    telemetry.addData("This is reverse motion for the lift", 0);
//                }

                telemetry.addData("trigger", gamepad1.right_trigger);
                telemetry.addData("currentH", currentH);
                // telemetry.addData(0, lift.getDirection());

                telemetry.addData("DR4Motor", DR4BMotor);

                telemetry.update();


            }
        }
    }
}
