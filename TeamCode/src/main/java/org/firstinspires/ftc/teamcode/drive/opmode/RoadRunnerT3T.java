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

        Trajectory traj1 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-35, -30, Math.toRadians(0)))

                //.lineToSplineHeading(new Pose2d(-34, -16, Math.toRadians(90)))
                //.splineTo(new Vector2d(-29, -11), Math.toRadians(45))
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
//            if(prgrmran = false) {//thiss loop prevents ramming into stuff if u dont reset pos befor running code again
//                myLocalizer.followTrajectory(traj1);
//            }
//            prgrmran = true;

            myLocalizer.followTrajectory(traj1);
        }
    }
}