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

@Autonomous(name = "6010 April Tag Left Autonomous")
public class AprilTagAutonLeft extends LinearOpMode
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
        myLocalizer.setPoseEstimate(new Pose2d(-35,-61.5, Math.toRadians(90))); //start pos//heading in rads

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

        //push cone out of the way EITHER junc
        Trajectory trL1 = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35.5, -6.5))
                .build();
        //trajectiores that end in j(josh calling card)
        //double sts = angles.firstAngle;
        Trajectory trL1j = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(40, 40, Math.toRadians(90)))
                .build();



        //LOW JUNCTION parts 2-6

        //move back near low junc to prepare to score & face cone stack
        Trajectory trL2Low = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -6.5, Math.toRadians(90)))
                .lineToSplineHeading(new Pose2d(-34, -12, Math.toRadians(180)))
                .build();

//        Trajectory trL2Lowj = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -6.5, Math.toRadians(90)))
//                .splineTo(new Vector2d(-34,-12),Math.toRadians(180))
//                .build();

        Pose2d pose3 = new Pose2d(-44, -20, Math.toRadians(225));
        //WITHIN LOOP:
        //turn and move to low junc while scoring
        Trajectory trL3Low = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineToSplineHeading(pose3)
                .build();
        Trajectory trL3Lowj = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .splineTo(new Vector2d(-43, -17), Math.toRadians(225))
                .build();

        //angle back to face cone stack LOW junc after scoring
        Trajectory trL456Park2Low = myLocalizer.trajectoryBuilder(pose3)
                .lineToSplineHeading(new Pose2d(-34, -13.5, Math.toRadians(180)))
                .build();
        Trajectory trL456Park2Lowj = myLocalizer.trajectoryBuilder(new Pose2d(-43, -16, Math.toRadians(225)))
                .splineTo(new Vector2d(-34, -12), Math.toRadians(180))
                .build();


        //move forward to intake a cone EITHER junc
        Trajectory trL7 = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-66, -12))
                .build();

        //move backward from cone stack
        Trajectory trL8Low = myLocalizer.trajectoryBuilder(new Pose2d(-66, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-34, -12))
                .build();

        //END LOOP

        //LOW JUNCTION parking 1 and 3
        Trajectory trLPark1Low = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-59, -12))
                .build();

        Trajectory trLPark3Low = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineToSplineHeading(new Pose2d(-13.5, -12, Math.toRadians(180)))
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
        myLocalizer.followTrajectory(trL1); //move forward (far) to push cone out of the way
        //might have to reconsider pushing it that far forward in case opposing robot gets in the way
//
        myLocalizer.followTrajectory(trL2Low); //move back into middle block next to low junc, facing cone stack
        int lowJunc = 305;
        lift.setTargetPosition(lowJunc); //height for low junc
        lift.setVelocity(900);
        myLocalizer.followTrajectory(trL3Low); //angles over the low junction to score the cone
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
            int stackPos = 230; //starting stack height
            for (int i = 0; i < 1; i++) { //just doing once to reduce chance of it going wrong
                //executes 3 times for 3 cones for now
                //make this a spline?
                lift.setTargetPosition(stackPos);
                lift.setVelocity(900);
                myLocalizer.followTrajectory(trL7); //go forward to cone stack to intake
                //intake a cone
                //lower lift - ADD CODE
                lift.setTargetPosition(stackPos-50); //lowering to grab cone from stack
                intakeUp(1);
                sleep(1000); //1 second?
                intakeStop();
                lift.setTargetPosition(stackPos+30); //lifting above stacks
                lift.setVelocity(900);
                myLocalizer.followTrajectory(trL8Low); //go back to prepare to score
                //might lower the lift down completely here before raising it to low junc
                lift.setTargetPosition(300); //height for low junc
                lift.setVelocity(900);
                myLocalizer.followTrajectory(trL3Low); //turn to drop a cone on low junction
                intakeDown(1);
                sleep(1000);
                lift.setTargetPosition(0);
                lift.setVelocity(900);
                intakeStop();
                myLocalizer.followTrajectory(trL456Park2Low); //angle back to face cone stack
                stackPos -= 30; //decreases as each cone gets removed
            }

            if (tagOfInterest.id == LEFT) //tag = 1 detected, left side park
            {
                lift.setTargetPosition(0); //lower lift
                lift.setVelocity(700);
//                lift.setTargetPosition(70); //above cones in case we bump into stuff
//                lift.setVelocity(900);
                myLocalizer.followTrajectory(trLPark1Low);
//                lift.setTargetPosition(0); //lower lift
//                lift.setVelocity(600);

            } else if (tagOfInterest.id == MIDDLE) //tagOfInterest == null doesn't work-- its always false
            //if theres no tag detected or if its the middle one so tag = 2, middle park
            {
                //do nothing bc its already parked here
                lift.setTargetPosition(0); //lower lift
                lift.setVelocity(700);
//
            } else if (tagOfInterest.id == RIGHT) //tag = 3 detected, right side park
            {
//                lift.setTargetPosition(70); //above cones in case we bump into stuff
//                lift.setVelocity(900);
                myLocalizer.followTrajectory(trLPark3Low);
                lift.setTargetPosition(0); //lower lift
                lift.setVelocity(700);
//
            } else {
                //already accounted for
                lift.setTargetPosition(0); //lower lift
                lift.setVelocity(600);
                //auton fails, cant recognize qrcode, just move forward to middle zone
//            myLocalizer.followTrajectory(moveToPark);
            }
//        }

//        if (side.equals("right")) {
            //add all the trajectories here for if we start autonomous on the right side
            //should be the same y coordinates but different x

//        }
//        else { //left/right side was never selected, then move to park
//            myLocalizer.followTrajectory(moveToPark);
//            if (tagOfInterest.id == LEFT) {
//                myLocalizer.followTrajectory(leftPark);
//            }
//            else if (tagOfInterest.id == RIGHT) {
//                myLocalizer.followTrajectory(rightPark);
//            }
//        }

        //old stuff from the example, not used in video
//        if(tagOfInterest == null)
//        {
//            /*
//             * Insert your autonomous code here, presumably running some default configuration
//             * since the tag was never sighted during INIT
//             */
//        }
//            myLocalizer.update();
//
//            // Retrieve your pose
//            Pose2d myPose = myLocalizer.getPoseEstimate();
//
//            telemetry.addData("x", myPose.getX());
//            telemetry.addData("y", myPose.getY());
//            telemetry.addData("heading", myPose.getHeading());
//        else
//        {
////            /*
////             * Insert your autonomous code here, probably using the tag pose to decide your configuration.
////             *
////             */
////
//
//            // e.g.
//
//        }


        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
//        while (opModeIsActive()) {sleep(20);}
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