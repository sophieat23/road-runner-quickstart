
        /*
         * Copyright (c) 2021 OpenFTC Team
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy
         * of this software and associated documentation files (the "Software"), to deal
         * in the Software without restriction, including without limitation the rights
         * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
         * copies of the Software, and to permit persons to whom the Software is
         * furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all
         * copies or substantial portions of the Software.
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
         * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
         * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
         * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
         * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
         * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
         * SOFTWARE.
         */

        package org.firstinspires.ftc.teamcode.thunderstone.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.thunderstone.autonomous.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.Vector;


//for imu -delete the grey ones later
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous(name = "6010 Auton Right One Cone ")
public class AutonRightOneCone extends LinearOpMode
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    public AprilTagDetection getTagOfInterest() {
        return tagOfInterest;
    }
    boolean prgrmran = false; //unnecessary to roadrunner but i have this so the program only runs once later
    //josh why is it spelled like this
    public static double DISTANCE = 14;//is in feet

    private CRServo leftServo;
    private CRServo rightServo;
    private DcMotorEx lift;

    private double DR4BMotor;

    //4 imu
    BNO055IMU imu;
    Orientation angles;

    private void intakeUp(double pow) {
        leftServo.setPower(pow);
        rightServo.setPower(-pow);
    }
    private void intakeDown(double pow) {
        leftServo.setPower(-pow);
        rightServo.setPower(pow);
    }
    private void intakeStop() {
        leftServo.setPower(0);
        rightServo.setPower(0);
    }

    @Override
    public void runOpMode() //may add thrwos interupted exceptinon
    {
        //4imu
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);



        //RDR.
        // Insert whatever initialization your own code does
        SampleMecanumDrive myLocalizer = new SampleMecanumDrive(hardwareMap);
        // This is assuming you're using StandardTrackingWheelLocalizer.java
        // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your configuration is different
        // Set your initial pose to x: 10, y: 10, facing 90 degrees
        Pose2d startPose = new Pose2d(35,-64, Math.toRadians(90));
        myLocalizer.setPoseEstimate(startPose); //start pos//heading in rads

        lift = hardwareMap.get(DcMotorEx.class, "DR4B");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentH= lift.getCurrentPosition();
        lift.setTargetPosition(currentH);

        boolean wasHigh;
        boolean wasMed;

        DR4BMotor = 0;

        lift.setDirection(DcMotorEx.Direction.REVERSE);
        lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);


        //wanted to use vars but i dont think it would work
//        Pose2d currentPose = new Pose2d(-35,-61.5, Math.toRadians(90));
//        Vector2d currentVector = new Vector2d(-35.5, -7.3);
//        double rSideHeading = Math.toRadians(90);

        double xPushedTo = 35.5;
        double yPushedTo = -6.5; //6.5

        //push cone out of the way EITHER junc
        Trajectory trL1 = myLocalizer.trajectoryBuilder(startPose) //starting pos
                .lineTo(new Vector2d(xPushedTo, yPushedTo))
                .build();
        //trajectiores that end in j(josh calling card)
//        //double sts = angles.firstAngle;
//        Trajectory trL1j = myLocalizer.trajectoryBuilder(new Pose2d(35,-61.5, Math.toRadians(90)))
//                .lineToLinearHeading(new Pose2d(40, 40, Math.toRadians(90)))
//                .build();


//        double rSideHeading = Math.toRadians(90);


        //LOW JUNCTION parts 2-6
        Pose2d pose2 = new Pose2d(34, -12, Math.toRadians(0));
        //x was 36 before

        //move back near low junc to prepare to score & face cone stack
        Trajectory trL2Low = myLocalizer.trajectoryBuilder(new Pose2d(xPushedTo, yPushedTo, Math.toRadians(90)))
                .lineToSplineHeading(pose2)
                .build();

//        Trajectory trL2Lowj = myLocalizer.trajectoryBuilder(new Pose2d(35.5, -6.5, Math.toRadians(90)))
//                .splineTo(new Vector2d(34,-12),Math.toRadians(180))
//                .build();

        Pose2d pose3 = new Pose2d(44, -20, Math.toRadians(-45));
        //WITHIN LOOP:
        //turn and move to low junc while scoring
        Trajectory trR3Low = myLocalizer.trajectoryBuilder(pose2)
                .lineToSplineHeading(pose3)
                .build();
//        Trajectory trL3Lowj = myLocalizer.trajectoryBuilder(new Pose2d(34, -12, Math.toRadians(180)))
//                .splineTo(new Vector2d(43, -17), Math.toRadians(225))
//                .build();

        //angle back to face cone stack LOW junc after scoring
        Pose2d pose456 = new Pose2d(37, -12, Math.toRadians(0));

        Trajectory trL456Park2Low = myLocalizer.trajectoryBuilder(pose3)
                .lineToSplineHeading(pose456)
                .build();
//        Trajectory trL456Park2Lowj = myLocalizer.trajectoryBuilder(new Pose2d(43, -16, Math.toRadians(225)))
//                .splineTo(new Vector2d(34, -12), Math.toRadians(180))
//                .build();

        double x7 = 67;
        double  y7 = -12;

        //move forward to intake a cone EITHER junc
        Trajectory trL7 = myLocalizer.trajectoryBuilder(pose456)
                .lineTo(new Vector2d(x7, y7))
                .build();

        double x8 = 32; //34
        double y8 = -12;

        //move backward from cone stack
        Trajectory trL8Low = myLocalizer.trajectoryBuilder(new Pose2d(x7, y7, Math.toRadians(0)))
                .lineTo(new Vector2d(x8, y8))
                .build();

        //END LOOP

        double park3X = 61;
        double park3Y = -12;

        double park1X = 15;
        double park1Y = -12;

        //LOW JUNCTION parking 1 and 3
        Trajectory trLPark1Low = myLocalizer.trajectoryBuilder(pose456)
                .lineTo(new Vector2d(park1X, park1Y))
                .build();

        Trajectory trLPark3Low = myLocalizer.trajectoryBuilder(pose456)
                .lineToSplineHeading(new Pose2d(park3X, park3Y, Math.toRadians(0)))
                .build();

//
//        //simple parking from start trajectories
//        Trajectory moveToPark = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
//                .lineTo(new Vector2d(-35, -35))
//                .build();
//        Trajectory leftPark = myLocalizer.trajectoryBuilder(new Pose2d(-35, -35, Math.toRadians(90)))
//                .strafeTo(new Vector2d(-59, -35))
//                .build();
//        Trajectory rightPark = myLocalizer.trajectoryBuilder(new Pose2d(-35, -35, Math.toRadians(90)))
//                .strafeTo(new Vector2d(-12.5, -35))
//                .build();

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

//            int state = 0;
//            String side = "no";
//            telemetry.addData("Select left or right side start with the arrow buttons. " +
//                    "This must be done before hitting start!", " ");
//            telemetry.update();
//            if (gamepad1.dpad_left) {
//                side = "left";
//                state = 1;
//            } else if (gamepad1.dpad_right) {
//                side = "right";
//                state = 1;
//            } else {
//                state = 1;
//            }
//            telemetry.addData(side + " side selected.", " ");
//            telemetry.update();

//            if (state == 1) {
            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }
//            }
            //4 imu
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,AngleUnit.DEGREES);
            telemetry.addData("heading", angles.firstAngle); //-perpendiculer to the floor, we use this
            telemetry.addData("Roll", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            //
            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }


//        String side = "left";

//        if (side.equals("left")) {
        //general auton path with or without sighting zone
        lift.setTargetPosition(0); //starting w/ lift not extended now to prevent inaccuracies in pathing
        //assuming this doesn't interfere w/ signal cone
        //might have to lift it so it avoids cone
//            lift.setTargetPosition(80); //above the cone
//            lift.setVelocity(900); //arbitrary val for now
//            myLocalizer.followTrajectory(trL1j); //move forward to push cone

        telemetry.update();
        //this telemetry update will be usefull i think if we use imu for inital angle for trL1
        intakeUp(1);
        sleep(800);
        intakeStop();
        myLocalizer.followTrajectory(trL1); //move forward (far) to push cone out of the way
        //might have to reconsider pushing it that far forward in case opposing robot gets in the way
        lift.setTargetPosition(30);
        lift.setVelocity(900);
//
        myLocalizer.followTrajectory(trL2Low); //move back into middle block next to low junc, facing cone stack
        int lowJunc = 315;
        lift.setTargetPosition(lowJunc); //height for low junc
        lift.setVelocity(900);
        myLocalizer.followTrajectory(trR3Low); //angles over the low junction to score the cone
        intakeDown(1);
        sleep(2000);
        leftServo.setPower(0);
        rightServo.setPower(0);
//            myLocalizer.followTrajectory(trL2Lowj); //move back and face cone stack
//            myLocalizer.followTrajectory(trL3Lowj); //turn to drop a cone on low junction

        myLocalizer.followTrajectory((trL456Park2Low)); //prob wouldn't work for parking in 2nd spot, too far back rn
        //angles heading back towards the cone junction to prepare to drive forward
//        lift.setTargetPosition(0); //height for low junc
//        lift.setVelocity(900);

//            myLocalizer.followTrajectory(trL456Park2Lowj); //facing cone stack, in the middle zone
        int stackPos = 210; //starting stack height
//        for (int i = 0; i < 2; i++) { //just doing once to reduce chance of it going wrong
//            //executes 3 times for 3 cones for now
//            //make this a spline?
//            lift.setTargetPosition(stackPos);
//            lift.setVelocity(900);
//            myLocalizer.followTrajectory(trL7); //go forward to cone stack to intake
//            //intake a cone
//            //lower lift - ADD CODE
//            lift.setTargetPosition(stackPos-60); //lowering to grab cone from stack
//            intakeUp(1);
//            sleep(700); //1 second?
//            intakeStop();
//            lift.setTargetPosition(stackPos+40); //lifting above stacks
//            lift.setVelocity(900);
//            myLocalizer.followTrajectory(trL8Low); //go back to prepare to score
//            //might lower the lift down completely here before raising it to low junc
//            sleep(500);
//            lift.setTargetPosition(300); //height for low junc
//            lift.setVelocity(900);
//            myLocalizer.followTrajectory(trR3Low); //turn to drop a cone on low junction
//            intakeDown(1);
//            sleep(700);
//            intakeStop();
//            myLocalizer.followTrajectory(trL456Park2Low); //angle back to face cone stack
//            lift.setTargetPosition(0);
//            lift.setVelocity(900);
//            stackPos -= 30; //decreases as each cone gets removed
//        }

        if (tagOfInterest.id == LEFT) //tag = 1 detected, left side park
        {

//                lift.setTargetPosition(70); //above cones in case we bump into stuff
//                lift.setVelocity(900);
            myLocalizer.followTrajectory(trLPark1Low);
//                lift.setTargetPosition(0); //lower lift
//                lift.setVelocity(600);

        } else if (tagOfInterest.id == MIDDLE) //tagOfInterest == null doesn't work-- its always false
        //if theres no tag detected or if its the middle one so tag = 2, middle park
        {
            //do nothing bc its already parked here

//
        } else if (tagOfInterest.id == RIGHT) //tag = 3 detected, right side park
        {
//                lift.setTargetPosition(70); //above cones in case we bump into stuff
//                lift.setVelocity(900);
            myLocalizer.followTrajectory(trLPark3Low);

//
        } else {
            //already accounted for

            //auton fails, cant recognize qrcode, just move forward to middle zone
//            myLocalizer.followTrajectory(moveToPark);
        }
//
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}