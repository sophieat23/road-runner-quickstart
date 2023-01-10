package org.firstinspires.ftc.teamcode.thunderstone.teleop;//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//public class CommentedCode {
//
//
//    private double slow = .5;
//    private double slowR = 1; //slow for rotating
//    private double y = 0;
//    private double equationY = 0;
//    private double x = 0;
//    private double equationX = 0;
//    //    private LinearSlide linearSlide = new LinearSlide();
//    private RubberBandIntake intake = new RubberBandIntake();
//    private Drive drive = new Drive();
//    private CRServo leftServo = intake.getLeftServo();
//    private CRServo rightServo = intake.getRightServo();
//    private DcMotorEx lift;
////    private DcMotorEx lsMotor = linearSlide.getLsMotor();
//
//    private DcMotorEx frontLeft;
//    private DcMotorEx frontRight;
//    private DcMotorEx backLeft;
//    private DcMotorEx backRight;
//
//    private double BRMotor;
//    private double BLMotor;
//    private double FRMotor;
//    private double FLMotor;
//
//    private double DR4BMotor;
//
//    public void runOpMode() {
////    public void init() {
////        linear slide
////        lsMotor = hardwareMap.get(DcMotorEx.class, "linearSlide");
////        note: not using linear slide right now
///*
//        intake servos
//        note: currently treating servos as instance variables within this class,
//        maybe should try treating them as instance data in the RubberBandIntake class-->
//        would have to change methods
// */
//        leftServo = hardwareMap.get(CRServo.class, "leftServo");
//        rightServo = hardwareMap.get(CRServo.class, "rightServo");
//
//        //lift:
//        lift = hardwareMap.get(DcMotorEx.class, "DR4B");
//        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        int currentH= lift.getCurrentPosition();
//        lift.setTargetPosition(currentH);
//
//        //wheels
//        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
//        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
//        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
//        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
//
//        BLMotor = 0;
//        BRMotor = 0;
//        FLMotor = 0;
//        FRMotor = 0;
////    }
////    public void loop() {
//
///*
//        lsMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        lsMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//
// */
//        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
//        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
//        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
//
//        lift.setDirection(DcMotorEx.Direction.REVERSE);
//
//
//        // leftServo.setDirection().FORWARD;
//        // rightServo.setDirection().REVERSE;
//
//        waitForStart();
//
//        if (opModeIsActive()) {
//            // Put run blocks here.
//            while (opModeIsActive()) {
//
//                BLMotor = 0;
//                BRMotor = 0;
//                FLMotor = 0;
//                FRMotor = 0;
//                DR4BMotor = 0;
//
//
//
//                //slow:
//                if(gamepad1.y){
//                    slow = .25;
//                    slowR = .5;
//                } else {
//                    slow = .5;
//                    slowR = 1;
//                }
//
//                /*
//                gamepad input
//                 */
//
////                if (gamepad1.dpad_up) {
////                    lsMotor.setPower(.3); //arbitrary power val, just to test linear slide
////                }
//                /*
//
//                servo buttons (OLD SERVO CODE SOMETHING DOESNT WORK NEED TO FIGURE OUT)
//                 */
//                // if (gamepad1.dpad_up) {
//                //     intake.pickUpCone(leftServo, rightServo, 1);
//                // }
//
//                // if (gamepad1.dpad_down) {
//                //     intake.dropCone(leftServo, rightServo, 1);
//                // }
//
//                //temp servo code:
//                if (gamepad1.dpad_up) {
//                    leftServo.setPower(1);
//                    rightServo.setPower(-1);
//                } else if (gamepad1.dpad_down) {
//                    leftServo.setPower(-1);
//                    rightServo.setPower(1);
//                } else {
//                    leftServo.setPower(0);
//                    rightServo.setPower(0);
//                }
//
//                //   //TEMPORARY SERVO CODE UNTIL CLASSES ARE FIXED
//
//                //   //right servo
//                //   if(gamepad1.dpad_down){
//                //         rightServo.setPower(1);
//
//                //     }else if(gamepad1.dpad_up){
//                //         rightServo.setPower(-1);
//
//                //     } else {
//                //         rightServo.setPower(0);
//                //     }
//
//                //     //leftservo
//                //     if(gamepad1.dpad_down){
//                //         leftServo.setPower(-1);
//
//                //     }else if(gamepad1.dpad_up){
//                //         leftServo.setPower(1);
//
//                //     } else {
//                //         leftServo.setPower(0);
//                //     }
//
//
//
//
//                /* spinning left
//                try to make it spin to pick up a cone, lying w/ base on the right
//                right intake spins left
//                left intake spins left
//                remember: right button for when the cone base is on the right
//                change this^
//                 */
//                // if (gamepad1.x) {
//                //     intake.fallenConeLeft(leftServo, rightServo, 1);
//                // }
//                /* spinning right
//                try to make it spin to pick up a cone, lying w/ base on the left
//                right intake spins right
//                left intake spins right
//                remember: left button for when the cone base is on the left
//                change this^
//                 */
//                // if (gamepad1.b) {
//                //     intake.fallenConeRight(leftServo, rightServo, 1);
//                // }
//
//                // else if{
//                //     intake.turnOffServos(leftServo, rightServo); //sets pow to zero
//                // }
//
//                /*
//                joystick controls
//                 */
//                y = gamepad1.left_stick_y ; // *slow
//                x =  gamepad1.left_stick_x; //*slow
//
//                equationY = .75 * drive.pow(y, 3) + .25 * y;
//                equationX = .75 * drive.pow(x, 3) + .25 * x;
//
//                //forward/backward
//                BLMotor += -equationY * slow;
//                BRMotor += -equationY * slow;
//                FLMotor += -equationY * slow;
//                FRMotor += -equationY * slow;
//
//                //strafing:
//                FLMotor += equationX ;//*slow
//                BLMotor += -equationX ;
//                FRMotor += -equationX ;
//                BRMotor += equationX ;
//
//
//                //rotating w/o slow mode
//                FLMotor += (gamepad1.right_stick_x)  * .5 *slowR; //.5 * slow
//                BLMotor += (gamepad1.right_stick_x)  * .5 * slowR;
//                FRMotor += (-gamepad1.right_stick_x)  * .5 * slowR;
//                BRMotor += (-gamepad1.right_stick_x)  * .5 * slowR;
//
//                backLeft.setPower(BLMotor);
//                backRight.setPower(BRMotor);
//                frontLeft.setPower(FLMotor);
//                frontRight.setPower(FRMotor);
//
//                //lift:
//                if(gamepad1.left_trigger>0 && currentH<1000) { //up (and h<1000 is the max height)
//                    lift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//                    DR4BMotor = gamepad1.left_trigger*1200;
//                    lift.setVelocity(DR4BMotor);
//
//                    //for some reason NEED this line of code to work:
//                    telemetry.addData("Outake Pow", lift.getPower());
//
//                    currentH= lift.getCurrentPosition();
//                    // fightGravity = .5;
//
//
//                } else if(gamepad1.right_trigger>.01 && lift.getCurrentPosition() >50){//down
//                    // lift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//                    // DR4BMotor = - gamepad1.right_trigger*700;
//
//                    // lift.setVelocity(DR4BMotor);
//
//                    // //for some reason NEED this line of code to work:
//                    // telemetry.addData("Outake Pow", lift.getPower());
//
//                    lift.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODERS);
//                    currentH= lift.getCurrentPosition();
//
//                    if(currentH > 300){
//                        lift.setPower(.001);//gravity is doing most of the work so only need a little power to keep it from falling down too fast
//                    } else if(currentH > 150){
//                        lift.setPower(.0005);//need less power because doesn't have as much gravitational potential energy
//                    } else{
//                        lift.setPower(-.0001);//gravity doesnt do as much work here so power needs to be set to negative
//                    }
//
//
//
//
//                } else if (gamepad1.x) { //middle preset
//                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//                    lift.setTargetPosition(1000);
//                    currentH = 1000;
//                    lift.setPower(.5); //try setting this to velocity
//                    //power
//                    // if(currentH > 300){
//                    //     lift.setPower(.001);//gravity is doing most of the work so only need a little power to keep it from falling down too fast
//                    // } else if(currentH > 150){
//                    //     lift.setPower(.0005);//need less power because doesn't have as much gravitational potential energy
//                    // } else{
//                    //     lift.setPower(-.0001);//gravity doesnt do as much work here so power needs to be set to negative
//                    // }
//
//                    telemetry.addData("velocity from lift", lift.getVelocity());
//
//
//                } else if (gamepad1.b){ //highest preset
//                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//                    lift.setTargetPosition(500);
//                    currentH = 500;
//                    lift.setPower(.5);
//                    //power
//                    // if(currentH > 300){
//                    //     lift.setPower(.001);//gravity is doing most of the work so only need a little power to keep it from falling down too fast
//                    // } else if(currentH > 150){
//                    //     lift.setPower(.0005);//need less power because doesn't have as much gravitational potential energy
//                    // } else{
//                    //     lift.setPower(-.0001);//gravity doesnt do as much work here so power needs to be set to negative
//                    // }
//
//                } else if(gamepad1.a){ //lowest preset
//                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//                    lift.setTargetPosition(50);
//                    currentH = 50;
//                    lift.setPower(.5);
//                    //power
//                    // if(currentH > 300){
//                    //     lift.setPower(.001);//gravity is doing most of the work so only need a little power to keep it from falling down too fast
//                    // } else if(currentH > 150){
//                    //     lift.setPower(.0005);//need less power because doesn't have as much gravitational potential energy
//                    // } else{
//                    //     lift.setPower(-.0001);//gravity doesnt do as much work here so power needs to be set to negative
//                    // }
//
//                } else {
//
//                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//                    lift.setTargetPosition(currentH);
//
//                }
//
//                telemetry.addData("trigger", gamepad1.right_trigger);
//                telemetry.addData("currentH", currentH);
//
//                telemetry.addData("DR4Motor", DR4BMotor);
//
//                telemetry.update();
//
//
//            }
//        }
//    }
//
//}
