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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@TeleOp(name = "6010 April Tag Example")
public class AprilTagAutonomousInitDetectionExample extends LinearOpMode
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

        Trajectory parkLeft = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35, -35))
                .lineTo(new Vector2d(-60, -35))
                .build();
        Trajectory tr1 = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35, -7.3))
//                .forward(55)
                .build();
        Trajectory tr2 = myLocalizer.trajectoryBuilder(new Pose2d(-35, -7.3, Math.toRadians(90)))
                .lineTo(new Vector2d(-35, -36.3))
//                .back(30)
                .build();

        Trajectory traj1 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-34, -16, Math.toRadians(90)))
                .splineTo(new Vector2d(-29, -11), Math.toRadians(45))
                //^this would simulate going infornt of the pole and raising the
                .build();

        Trajectory traj2 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-29, -5, Math.toRadians(45)))
                //^go from in front of pole to atop
                .build();

        Trajectory traj3 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-31, -11, Math.toRadians(45)))
                //^back up
                .build();

        Trajectory traj4 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-58.5,-12, Math.toRadians(180)))
                //go in front of cones for pickup
                .build();

        Trajectory traj5 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-60.5,-12, Math.toRadians(180)))
                //go atop cones
                .build();
        //pickup cone

        Trajectory traj6 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-58.5,-12, Math.toRadians(180)))
                .build();
        //back up ^
        //lower lift - do- write that

        Trajectory traj7 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-29, -11, Math.toRadians(45)))
                .build();

        Trajectory zone1 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-11, -11, Math.toRadians(90)))
                .build();
        Trajectory zone2 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-34, -11, Math.toRadians(90)))
                .build();
        Trajectory zone3 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-57, -11, Math.toRadians(90)))
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

//        /* Actually do something useful */
//        if(tagOfInterest == null)
//        {
//            /*
//             * Insert your autonomous code here, presumably running some default configuration
//             * since the tag was never sighted during INIT
//             */
//        }
//        else
//        {
//            /*
//             * Insert your autonomous code here, probably using the tag pose to decide your configuration.
//             *
//             */

            myLocalizer.update();

            // Retrieve your pose
            Pose2d myPose = myLocalizer.getPoseEstimate();

            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());

            // e.g.
            if(tagOfInterest.id == LEFT) //tag = 1 detected, left side park
            {
                // do something - traj
                myLocalizer.followTrajectory(parkLeft);
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
            else if(tagOfInterest == null || tagOfInterest.id == MIDDLE)
                //if theres no tag detected or if its the middle one so tag = 2, middle park
            {
                // do something else - traj
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
                // do something else - traj
//                myLocalizer.followTrajectory(traj1);
//                myLocalizer.followTrajectory(traj2);
//                myLocalizer.followTrajectory(traj3);
//                myLocalizer.followTrajectory(traj4);
//                myLocalizer.followTrajectory(traj5);
//                myLocalizer.followTrajectory(traj6);
//                myLocalizer.followTrajectory(traj7);
//                myLocalizer.followTrajectory(zone1);
            }
//        }


        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
        while (opModeIsActive()) {sleep(20);}
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