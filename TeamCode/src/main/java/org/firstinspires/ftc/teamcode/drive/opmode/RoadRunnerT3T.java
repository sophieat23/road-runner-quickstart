package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(group = "drive")
public class RoadRunnerT3T extends LinearOpMode {

    boolean prgrmran = false; //unecesary to roadrunner but i have this so the programe only runs once later


    public static double DISTANCE = 14;//is in feet

    @Override
    public void runOpMode() throws InterruptedException {
        // Insert whatever initialization your own code does
        SampleMecanumDrive myLocalizer = new SampleMecanumDrive(hardwareMap);
        // This is assuming you're using StandardTrackingWheelLocalizer.java
        // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your configuration is different
        // Set your initial pose to x: 10, y: 10, facing 90 degrees
        myLocalizer.setPoseEstimate(new Pose2d(-35,-61.5, Math.toRadians(90))); //start pos//heading in rads

        Trajectory trajectorySplineRed = myLocalizer.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(-36.5, -23), Math.toRadians(90))
                .splineTo(new Vector2d(-61.5, -11.6), Math.toRadians(180))
                .build();

        waitForStart();


        while(opModeIsActive()) {
            // Make sure to call myLocalizer.update() on *every* loop
            // Increasing loop time by utilizing bulk reads and minimizing writes will increase your odometry accuracy
            myLocalizer.update();

            // Retrieve your pose
            Pose2d myPose = myLocalizer.getPoseEstimate();

            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());

            // Insert whatever teleop code you're using - dosent have to be in loop or conditional
            if(prgrmran = false) {//thiss loop prevents ramming into stuff if u dont reset pos befor running code again
                myLocalizer.followTrajectory(trajectorySplineRed);
            }
            prgrmran = true;

        }
    }
}