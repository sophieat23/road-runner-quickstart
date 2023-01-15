package org.firstinspires.ftc.teamcode.thunderstone.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

public class Trajectories { //just putting some extra trajectories here to clean things up
    /*
    //MID JUNCTION parts 2-6

    //move back near mid junc to prepare to score
    Trajectory trL2Mid = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -7.3, Math.toRadians(90)))
            .lineTo(new Vector2d(-35.5, -36.3))
            .build();

    //turn and move to mid junc while scoring
    Trajectory trL3Mid = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -36.3, Math.toRadians(90)))
            .lineToSplineHeading(new Pose2d(-30, -30, Math.toRadians(45)))
            .build();

    //move back out of the way and rotate MID junc
    Trajectory trL4Mid = myLocalizer.trajectoryBuilder(new Pose2d(-30, -30, Math.toRadians(45)))
            .lineToSplineHeading(new Pose2d(-35.5, -36.3, Math.toRadians(90)))
            .build();

    //move forward to clear junctions before rotating MID junc
    Trajectory trL5Mid = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -36.3, Math.toRadians(90)))
            .lineTo(new Vector2d(-35.5, -20))
            .build();

    //turn left 90 while moving to prepare for the stack MID junc
    Trajectory trL6Mid = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -20, Math.toRadians(90)))
            .lineToSplineHeading(new Pose2d(-34, -12, Math.toRadians(180)))
            //using -35.5 as x may work better
            .build();

            //MID JUNCTION parts 8-10 and parking

        //move backward to prepare to outtake MID junc
        Trajectory trL8Mid = myLocalizer.trajectoryBuilder(new Pose2d(-60, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-10, -12))
//                .lineTo(new Vector2d(-60, -12))
                .build();

        //angle onto the MID junc
        Trajectory trL9Mid = myLocalizer.trajectoryBuilder(new Pose2d(-10, -12, Math.toRadians(180)))
                .lineToSplineHeading(new Pose2d(-17, -16, Math.toRadians(225)))
                .build();

        //angling back for mid junc/parking zone 3 for MID junc
        //have to do this before park 1 or 2
        Trajectory trL10Park3Mid = myLocalizer.trajectoryBuilder(new Pose2d(-17, -16, Math.toRadians(225)))
                .lineToSplineHeading(new Pose2d(-10, -12, Math.toRadians(180)))
                .build();

        //getting back in the middle/parking zone 2 for MID junc
        Trajectory trLPark2Mid = myLocalizer.trajectoryBuilder(new Pose2d(-10, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-34, -12))
                .build();

        //OR parking in 1st zone for MID junc
        Trajectory trLPark1Mid = myLocalizer.trajectoryBuilder(new Pose2d(-10, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-59, -12))
                .build();


    //joshes trajectories
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

     */
}
