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

@Autonomous(name = "6010 April Tag Autonomous - USE THIS")
public class AprilTagAutonomous extends LinearOpMode
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

    @Override
    public void runOpMode()
    {
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

        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);

        //wanted to use vars but i dont think it would work
//        Pose2d currentPose = new Pose2d(-35,-61.5, Math.toRadians(90));
//        Vector2d currentVector = new Vector2d(-35.5, -7.3);

        //push cone out of the way EITHER junc
        Trajectory trL1 = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35.5, -7.3))
                .build();

        //LOW JUNCTION parts 2-6

        //move back near low junc to prepare to score & face cone stack
        Trajectory trL2Low = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -7.3, Math.toRadians(90)))
                .lineToSplineHeading(new Pose2d(-34, -12, Math.toRadians(180)))
                .build();

        //WITHIN LOOP:
        //turn and move to low junc while scoring
        Trajectory trL3Low = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineToSplineHeading(new Pose2d(-41, -16, Math.toRadians(225)))
                .build();

        //angle back to face cone stack LOW junc after scoring
        Trajectory trL456Park2Low = myLocalizer.trajectoryBuilder(new Pose2d(-41, -16, Math.toRadians(225)))
                .lineToSplineHeading(new Pose2d(-34, -12, Math.toRadians(180)))
                .build();

        //move forward to intake a cone EITHER junc
        Trajectory trL7 = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-63, -12))
                .build();

        //move backward from cone stack
        Trajectory trL8Low = myLocalizer.trajectoryBuilder(new Pose2d(-60, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-34, -12))
                .build();

        //END LOOP

        //LOW JUNCTION parking 1 and 3
        Trajectory trLPark1Low = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-59, -12))
                .build();

        Trajectory trLPark3Low = myLocalizer.trajectoryBuilder(new Pose2d(-34, -12, Math.toRadians(180)))
                .lineToSplineHeading(new Pose2d(-10, -12, Math.toRadians(180)))
                .build();


        //simple parking from start trajectories
        Trajectory moveToPark = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35, -35))
                .build();
        Trajectory leftPark = myLocalizer.trajectoryBuilder(new Pose2d(-35, -35, Math.toRadians(90)))
                .strafeTo(new Vector2d(-63, -35))
                .build();
        Trajectory rightPark = myLocalizer.trajectoryBuilder(new Pose2d(-35, -35, Math.toRadians(90)))
                .strafeTo(new Vector2d(-10, -35))
                .build();

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT ||tag.id == MIDDLE ||tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

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

        //general auton path with or without sighting zone
        lift.setTargetPosition(80); //above the cone
        lift.setVelocity(900); //arbitrary val for now
        myLocalizer.followTrajectory(trL1); //move forward to push cone
        lift.setTargetPosition(300); //height for low junc
        lift.setVelocity(900);
        myLocalizer.followTrajectory(trL2Low); //move back and face cone stack
        myLocalizer.followTrajectory(trL3Low); //turn to drop a cone on low junction
        leftServo.setPower(-1);
        rightServo.setPower(1);
        myLocalizer.followTrajectory(trL456Park2Low); //facing cone stack, in the middle zone
        leftServo.setPower(0);
        rightServo.setPower(0);
        int stackPos = 250; //starting stack height
        for (int i = 0; i < 4; i++) { //executes 4 times for 4 cones
            lift.setTargetPosition(stackPos);
            lift.setVelocity(900);
            myLocalizer.followTrajectory(trL7); //go forward to cone stack to intake
            //intake a cone
            leftServo.setPower(1);
            rightServo.setPower(-1);
            sleep(1000); //1 second?
            leftServo.setPower(0);
            rightServo.setPower(0);
            lift.setTargetPosition(300);
            lift.setVelocity(900);
            myLocalizer.followTrajectory(trL8Low); //go back to prepare to score
            myLocalizer.followTrajectory(trL3Low); //turn to drop a cone on low junction
            leftServo.setPower(-1);
            rightServo.setPower(1);
            myLocalizer.followTrajectory(trL456Park2Low); //angle back to face cone stack
            stackPos -= 30; //decreases as each cone gets removed
        }

        if(tagOfInterest.id == LEFT) //tag = 1 detected, left side park
        {
            lift.setTargetPosition(70); //above cones in case we bump into stuff
            lift.setVelocity(900);
            myLocalizer.followTrajectory(trLPark1Low);
            lift.setTargetPosition(0); //lower lift
            lift.setVelocity(600);
//            myLocalizer.followTrajectory(trL1);
//            myLocalizer.followTrajectory(trL2);
//            myLocalizer.followTrajectory(trL3);
//            myLocalizer.followTrajectory(trL4);
//            myLocalizer.followTrajectory(trL5);
//            for(int i = 0; i <2; i++){
//                myLocalizer.followTrajectory(trL6);
//                myLocalizer.followTrajectory(trL7);
//                myLocalizer.followTrajectory(trL8);
//                myLocalizer.followTrajectory(trL9);
//            }
            //unnecessary bc already in correct zone
//            myLocalizer.followTrajectory(trL10);

//            //SOPHIE's code to simply park
//            myLocalizer.followTrajectory(moveToPark);
//            myLocalizer.followTrajectory(leftPark);

            //JOSH's
//                myLocalizer.followTrajectory(tr1);
//                myLocalizer.followTrajectory(tr2);
//                myLocalizer.followTrajectory(traj1);
//                myLocalizer.followTrajectory(traj2);
//                myLocalizer.followTrajectory(traj3);
//                myLocalizer.followTrajectory(traj4);
//                myLocalizer.followTrajectory(traj5);
//                myLocalizer.followTrajectory(traj6);
//                myLocalizer.followTrajectory(traj7);
//                myLocalizer.followTrajectory(zone3);
        }
        else if(tagOfInterest.id == MIDDLE) //tagOfInterest == null doesn't work-- its always false
        //if theres no tag detected or if its the middle one so tag = 2, middle park
        {
            //do nothing bc its already parked here
            lift.setTargetPosition(0); //lower lift
            lift.setVelocity(600);
//            myLocalizer.followTrajectory(moveToPark);
//                myLocalizer.followTrajectory(traj1);
//                myLocalizer.followTrajectory(traj2);
//                myLocalizer.followTrajectory(traj3);
//                myLocalizer.followTrajectory(traj4);
//                myLocalizer.followTrajectory(traj5);
//                myLocalizer.followTrajectory(traj6);
//                myLocalizer.followTrajectory(traj7);
//                myLocalizer.followTrajectory(zone2);
        }
        else if(tagOfInterest.id == RIGHT) //tag = 3 detected, right side park
        {
            lift.setTargetPosition(70); //above cones in case we bump into stuff
            lift.setVelocity(900);
            myLocalizer.followTrajectory(trLPark3Low);
            lift.setTargetPosition(0); //lower lift
            lift.setVelocity(600);
//            myLocalizer.followTrajectory(moveToPark);
//            myLocalizer.followTrajectory(rightPark);
//                myLocalizer.followTrajectory(traj1);
//                myLocalizer.followTrajectory(traj2);
//                myLocalizer.followTrajectory(traj3);
//                myLocalizer.followTrajectory(traj4);
//                myLocalizer.followTrajectory(traj5);
//                myLocalizer.followTrajectory(traj6);
//                myLocalizer.followTrajectory(traj7);
//                myLocalizer.followTrajectory(zone1);
        } else {
            //already accounted for
            lift.setTargetPosition(0); //lower lift
            lift.setVelocity(600);
            //auton fails, cant recognize qrcode, just move forward to middle zone
//            myLocalizer.followTrajectory(moveToPark);
        }
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