
package org.firstinspires.ftc.teamcode.thunderstone.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Disabled
@Autonomous(name="6010 Autonomous", group="Linear Opmode")

public class FreightFrenzyAuto extends LinearOpMode {

    //imu initialization stuff:
    BNO055IMU imu;
    float heading;
    Orientation angles;

    private void checkOrientation(){
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        heading = angles.firstAngle;
        telemetry.addData("heading: ", heading);
        //telemetry.update();
    }

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx backright;
    private DcMotorEx backleft;
    private DcMotorEx frontleft;
    private DcMotorEx frontright;
    private DcMotorEx rightspinner;
    private DcMotorEx leftspinner;

    private DcMotorEx linearslide;
    private Servo crateSpin;

    private int pos;


    // NormalizedColorSensor colorSensor;

    private double BRMotor;
    private double BLMotor;
    private double FRMotor;
    private double FLMotor;
    private double RSPMotor;
    private double LSPMotor;

    private double rightSpinnerAddBy = 250; //old was 300 and 40 //old recent 250 and 30
    private double leftSpinnerAddBy = 250;

    //test:
    int timePassed;


    public void stopMoving() {
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
    }

    public void goLeft(double lp) {
        backleft.setPower(lp);
        backright.setPower(-lp);
        frontleft.setPower(-lp);
        frontright.setPower(lp);
    }

    public void goRight(double rp) {
        backleft.setPower(-rp);
        backright.setPower(rp);
        frontleft.setPower(rp);
        frontright.setPower(-rp);
    }

    public void goForward(double fp) {
        backleft.setPower(fp);
        backright.setPower(fp);
        frontleft.setPower(fp);
        frontright.setPower(fp);
    }

    public void goBackward(double bp) {
        backleft.setPower(-bp);
        backright.setPower(-bp);
        frontleft.setPower(-bp);
        frontright.setPower(-bp);
    }

    public void redSpin(){
        rightspinner.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftspinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightspinner.setDirection(DcMotorSimple.Direction.REVERSE);
        leftspinner.setDirection(DcMotorSimple.Direction.REVERSE);
        // for(int i = 0; i < 10; i++){
        RSPMotor = -rightSpinnerAddBy;
        telemetry.addData("Right Spinner Vel", rightspinner.getVelocity());
        rightSpinnerAddBy += 25;

        LSPMotor = -leftSpinnerAddBy;
        telemetry.addData("Left Spinner Vel", leftspinner.getVelocity());
        leftSpinnerAddBy += 25;

        rightspinner.setVelocity(RSPMotor);
        leftspinner.setVelocity(LSPMotor);
        // }
    }

    public void blueSpin(){
        rightspinner.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftspinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightspinner.setDirection(DcMotorSimple.Direction.FORWARD);
        leftspinner.setDirection(DcMotorSimple.Direction.FORWARD);
        // for(int i = 0; i < 10; i++){
        RSPMotor = -rightSpinnerAddBy;
        telemetry.addData("Right Spinner Pow", rightspinner.getVelocity());
        rightSpinnerAddBy += 25;

        LSPMotor = -leftSpinnerAddBy;
        telemetry.addData("Left Spinner Vel", leftspinner.getVelocity());
        telemetry.addData("leftSpinnerAddBy: ", leftSpinnerAddBy);
        leftSpinnerAddBy += 25;

        rightspinner.setVelocity(RSPMotor);
        leftspinner.setVelocity(LSPMotor);
        // }
    }

    public void stopSpin(){
        rightSpinnerAddBy = 250;
        leftSpinnerAddBy = 250;
        RSPMotor = 0;
        LSPMotor = 0;

        rightspinner.setVelocity(RSPMotor);
        leftspinner.setVelocity(LSPMotor);
    }

    public void turnLeft(double power){
        backleft.setPower(power);
        backright.setPower(-power);
        frontleft.setPower(power);
        frontright.setPower(-power);
    }

    public void turnRight(double power){
        backleft.setPower(-power);
        backright.setPower(power);
        frontleft.setPower(-power);
        frontright.setPower(power);
    }


    public void imuPivot(double goal, double pow, char dir){



        if(dir == 'r'){
            while(opModeIsActive() && heading > goal){
                checkOrientation();

                frontleft.setPower(pow); //. /2
                backleft.setPower(pow);
                // backright.setPower(-pow);


            }

        } else if (dir == 'l'){
            while(opModeIsActive() && heading < goal){
                checkOrientation();
                frontright.setPower(pow); //. /2
                backright.setPower(pow);

            }

        }




    }



    public void imuCorrect(double goal, double mlsec, double pow, char dir){
        while(opModeIsActive() && (mlsec - runtime.milliseconds()) > 0 ){
            telemetry.addData("mil", runtime.milliseconds());
            checkOrientation();
            if(goal-1.5 < heading && heading < goal +1.5){ //goal == heading (w/ 1.5 margin of error)
                telemetry.addData(" ", 222);

                if(dir == 'f'){
                    goForward(pow);
                } else if(dir == 'l'){
                    goLeft(pow);
                } else if(dir == 'r'){
                    goRight(pow);
                } else if (dir == 'b'){
                    goBackward(pow);
                }


                // //checkOrientation();
                // backright.setPower(pow);
                // frontleft.setPower(pow);
                // backleft.setPower(pow);
                // frontright.setPower(pow);

            } else if(heading > goal){
                //checkOrientation();
                backleft.setPower(pow);
                frontleft.setPower(pow);
                backright.setPower(0);
                frontright.setPower(0);
                //  sleep(180); //
                //  stopMoving();

                telemetry.addData(" ", 3);


            } else if (heading < goal){
                checkOrientation();
                backleft.setPower(0);
                frontleft.setPower(0);
                backright.setPower(pow);
                frontright.setPower(pow);
                //  sleep(180);
                //  stopMoving();

                telemetry.addData(" ", 4);

            }


            telemetry.update();

        }
        stopMoving();

    }

    public void linearSlideMove(double power) {

        linearslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        linearslide.setPower(power);
        telemetry.addData("Outake Pow", linearslide.getPower());


    }

    public void crateMove(){

        crateSpin.setPosition(1);
        telemetry.addData("Servo Pos", crateSpin.getPosition());
        sleep(1100);
        //.61
        //crateSpin.setPosition(0.0);
        crateSpin.setPosition(.63); //zero is really this number
        sleep(900);


    }
    public void slideLock(){

        pos = linearslide.getCurrentPosition();

        linearslide.setTargetPosition(pos);
        linearslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // linearslide.setPower(1000);
        //settargetpos

        telemetry.addData("linearslide Pos", pos);



    }




    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        backright = hardwareMap.get(DcMotorEx.class, "back right");
        backleft = hardwareMap.get(DcMotorEx.class, "back left");
        frontleft = hardwareMap.get(DcMotorEx.class, "front left");
        frontright = hardwareMap.get(DcMotorEx.class, "front right");
        leftspinner = hardwareMap.get(DcMotorEx.class, "left spinner");
        rightspinner = hardwareMap.get(DcMotorEx.class, "right spinner");
        crateSpin = hardwareMap.get(Servo.class, "crate spin");
        linearslide = hardwareMap.get(DcMotorEx.class, "linear slide");

        // Once per loop, we will update this hsvValues array. The first element (0) will contain the
        // hue, the second element (1) will contain the saturation, and the third element (2) will
        // contain the value. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
        // for an explanation of HSV color.
        // final float[] hsvValues = new float[3];


        // colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        // colorSensor.setGain(2.5f); //old value: 2.5

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        // backleft.setDirection(DcMotorSimple.Direction.REVERSE); //old
        // frontleft.setDirection(DcMotorSimple.Direction.REVERSE); //old

        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        frontright.setDirection(DcMotorSimple.Direction.REVERSE);

        leftspinner.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightspinner.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);


        frontright.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //put menu here

        boolean config = true;
        int state = 1;

        while (config) {

            switch (state) {

                case 1: //blue or red

                    telemetry.addData("Select red or blue side. Press X for blue and B for red.", " ");
                    telemetry.update();
                    if (gamepad1.x) { //blue

                        telemetry.addData("Blue side selected. To cancel, press down.", " ");
                        telemetry.update();
                        if (!gamepad1.dpad_down) {
                            state = 2; //continue on with selecting left or right for blue side
                        } else {
                            state = 1; //reselect blue/red side
                        }

                    } else if (gamepad1.b) { //red

                        telemetry.addData("Red side selected. To cancel, press down.", " ");
                        telemetry.update();
                        if (!gamepad1.dpad_down) {
                            state = 3; //continue on with selecting left or right for red side
                        } else {
                            state = 1; //reselect blue/red side
                        }

                    }
                    break;

                case 2: //blue side left or right

                    telemetry.addData("Blue side confirmed. Select left or right with the arrow buttons. Note: perspective:looking out onto field", " ");
                    telemetry.update();
                    if (gamepad1.dpad_left) {
                        telemetry.addData("Left side selected (blue). To cancel, press down.", " ");
                        telemetry.update();
                        if (!gamepad1.dpad_down) {
                            state = 4; //continue on with left blue side
                        } else {
                            state = 2; //reselect left/right blue side
                        }
                    } else if (gamepad1.dpad_right) {
                        telemetry.addData("Right side selected (blue). To cancel, press down.", " ");
                        telemetry.update();
                        if (!gamepad1.dpad_down) {
                            state = 5; //continue on with right blue side
                        } else {
                            state = 2; //reselect left/right blue side
                        }
                    }
                    break;

                case 3: //red side left or right

                    telemetry.addData("Red side confirmed. Select left or right with the arrow buttons.", " ");
                    telemetry.update();
                    if (gamepad1.dpad_left) {
                        telemetry.addData("Left side selected (red). To cancel, press down.", " ");
                        telemetry.update();
                        if (!gamepad1.dpad_down) {
                            state = 6; //continue on with left red side
                        } else {
                            state = 3; //reselect left/right red side
                        }
                    } else if (gamepad1.dpad_right) {
                        telemetry.addData("Right side selected (red). To cancel, press down.", " ");
                        telemetry.update();
                        if (!gamepad1.dpad_down) {
                            state = 7; //continue on with right red side
                        } else {
                            state = 3; //reselect left/right red side
                        }
                    }
                    break;

                case 4: //blue side left
                    telemetry.addData("Blue side left confirmed.", " ");
                    telemetry.update();
                    config = false;
                    //scan barcode, score, park in warehouse and move forward to make room
                    break;

                case 5: //blue side right
                    telemetry.addData("Blue side right confirmed.", " ");
                    telemetry.update();
                    config = false;
                    break;

                case 6: //red side left
                    telemetry.addData("Red side left confirmed.", " ");
                    telemetry.update();
                    config = false;
                    //spin ducks, scan barcode, score, park in storage unit
                    //scan barcode, score, spin ducks, park in storage unit
                    //spin ducks, scan barcode, score, park in warehouse
                    //scan barcode, score, spin ducks, park in warehouse
                    break;

                case 7: //red side right
                    telemetry.addData("Red side right confirmed.", " ");
                    telemetry.update();
                    config = false;
                    break;

            }

        }


        //more imu initialization stuff:
        imu = hardwareMap.get(BNO055IMU.class, "gyro");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        imu.initialize(parameters);

        telemetry.addData("heading:", heading);
        telemetry.update();


        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }



        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // while (opModeIsActive()){
        telemetry.addData("state", state);

        if (state == 4) { //blue side left.

            //start up
            linearSlideMove(.5);
            sleep(300);
            linearSlideMove(0);

            //go back to crate
            goBackward(.5);
            sleep(950); //old 900
            stopMoving();

            //pivot to align with crate
            imuPivot(-15,.5, 'r');
            stopMoving();

            //move linear slide up
            linearSlideMove(.5);
            sleep(3000); //2900
            linearSlideMove(.1);
            slideLock();

            crateMove();

            linearSlideMove(-.5);
            sleep(1000);
            linearSlideMove(0);

            imuPivot(-90,.5, 'r');
            stopMoving();

            sleep(500);

            goForward(1);
            sleep(1400);
            stopMoving();












            // //put scoring code her



            // goForward(.5);
            // sleep(1900);
            // stopMoving();

            // sleep(100);

            //  goRight(.5);
            // sleep(1000);
            // stopMoving();

            // goForward(.5);
            // sleep(500);
            // stopMoving();



        }

        if (state == 5) { //blue side right

            // //goal, mlsec, pow
            //  while(opModeIsActive()){
            //     imuCorrect(0,5000,-.25);

            // }



            //start up
            linearSlideMove(.5);
            sleep(300);
            linearSlideMove(0);

            //left
            goLeft(.5);
            sleep(500);
            stopMoving();

            //forward
            goForward(.5);
            sleep(900);
            stopMoving();

            //right slightly to get to spinner
            goRight(.5);
            sleep(300);
            stopMoving();

            //duck
            for(int i = 0; i < 300; i++){
                blueSpin();
            }
            stopSpin();
            sleep(500);

            // left to park
            goLeft(.5);
            sleep(1700);//1700 //old 1100
            stopMoving();
            sleep(100);

            goBackward(.5);
            sleep(900); //700 old//800 old
            stopMoving();
            sleep(100);

            linearSlideMove(.5);
            sleep(2700); //2500
            linearSlideMove(.1);
            slideLock();

            crateMove();

            linearSlideMove(-.5);



            goForward(.5);
            sleep(1000); //800
            stopMoving();

            goRight(.5);
            sleep(800);
            stopMoving();

            sleep(400);
            linearSlideMove(0);




        }

        if (state == 6) { //red side left

            //put scoring code here

            //   //strafe left
            // goLeft(.5); //random values, can be adjusted
            // sleep(1000);
            // stopMoving();

            //start up
            linearSlideMove(.5);
            sleep(300);
            linearSlideMove(0);

            goRight(.5);
            sleep(575);///600/500
            stopMoving();

            goForward(.5);
            sleep(925);//900
            stopMoving();


            //left slightly to get to spinner
            goLeft(.5);
            sleep(300); //old 100
            stopMoving();

            //duck
            for(int i = 0; i < 300; i++){
                redSpin();
            }
            stopSpin();
            sleep(500);

            // // left to park
            // goLeft(.5);
            // sleep(1900);//1700 //old 1100
            // stopMoving();
            // sleep(100);

            // goBackward(.5);
            // sleep(800);
            // stopMoving();
            // sleep(100);

            //go back slightly
            goBackward(.5);
            sleep(300);//100
            stopSpin();
            stopMoving();

            //
            imuPivot(0,.5,'r');
            stopMoving();
            sleep(500);

            //to storage unit
            goRight(.5);
            sleep(1625); //1900/1600
            stopMoving();
            sleep(500);



            goBackward(.5);
            sleep(700);//800
            stopMoving();
            sleep(100);

            linearSlideMove(.5);
            sleep(2700); //2500
            linearSlideMove(.1);
            slideLock();

            crateMove();

            linearSlideMove(-.5);


            goForward(.5);
            sleep(1200); //800
            stopMoving();

            goLeft(.5);
            sleep(800);
            stopMoving();

            sleep(400);
            linearSlideMove(0);

        }

        if (state == 7) { //red side right


            //start up
            linearSlideMove(.5);
            sleep(300);
            linearSlideMove(0);

            //go back to crate
            goBackward(.5);
            sleep(950); //old 900
            stopMoving();

            //pivot to align with crate
            imuPivot(15,.5, 'l');
            stopMoving();

            //move linear slide up
            linearSlideMove(.5);
            sleep(3000); //2900
            linearSlideMove(.1);
            slideLock();

            crateMove();

            linearSlideMove(-.5);
            sleep(1000);
            linearSlideMove(0);

            imuPivot(90,.5, 'l');
            stopMoving();

            sleep(500);

            goForward(1);
            sleep(1400);
            stopMoving();






        }


    }
}