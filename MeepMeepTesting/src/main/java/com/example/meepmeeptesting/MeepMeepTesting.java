package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45.48291908330528, 45.48291908330528, Math.toRadians(180), Math.toRadians(180), 11.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -61.5, Math.toRadians(90)))
                                .forward(55)
                                .back(30)
                                .turn(-.75,39,39)
                                .forward(10)
                                //lift code here to middle height






//                                .lineToSplineHeading(new Pose2d(-34, -16., Math.toRadians(90)))
//                                .splineTo(new Vector2d(-29, -11), Math.toRadians(45))
//                                //^this would simulate going infornt of the pole and raising the
//                                //raise life
//                                .lineToSplineHeading(new Pose2d(-29, -5, Math.toRadians(45)))
//                                //drop cone
//                                .lineToSplineHeading(new Pose2d(-31, -11, Math.toRadians(45)))
//                                //^this would be after the thing rising
//                                //lower lift
//                                //below is 4stack
//                                .lineToSplineHeading(new Pose2d(-58.5,-12, Math.toRadians(180)))
//                                //^go to infront of cones
//                                //raise lift
//                                .lineToSplineHeading(new Pose2d(-60.5,-12, Math.toRadians(180)))
//                                //^go to ontop of cone
//                                //lower lift
//                                //get cone
//                                //raise lift
//                                .lineToSplineHeading(new Pose2d(-58.5,-12, Math.toRadians(180)))
//                                //lower lift
//                                .lineToSplineHeading(new Pose2d(-29, -11, Math.toRadians(45)))
//                                //raise lift
//                                .lineToSplineHeading(new Pose2d(-29, -5, Math.toRadians(45)))
//                                //drop cone
//                                .lineToSplineHeading(new Pose2d(-31, -11, Math.toRadians(45)))
//                                //^this would be after the thing rising
//                                //lower lift
//                                .lineToSplineHeading(new Pose2d(-58.5,-12, Math.toRadians(180)))
//                                //^go to infront of cones
//                                //raise lift
//                                .lineToSplineHeading(new Pose2d(-60.5,-12, Math.toRadians(180)))
//                                //^go to ontop of cone
//                                //lower lift
//                                //get cone
//                                //raise lift
//                                .lineToSplineHeading(new Pose2d(-58.5,-12, Math.toRadians(180)))
//                                //lower lift
//                                .lineToSplineHeading(new Pose2d(-29, -11, Math.toRadians(45)))
//                                //raise lift
//                                .lineToSplineHeading(new Pose2d(-29, -5, Math.toRadians(45)))
//                                //drop cone
//                                .lineToSplineHeading(new Pose2d(-31, -11, Math.toRadians(45)))
//                                //^this would be after the thing rising
//                                //lower lift
//
//
//                                //.lineToSplineHeading(new Pose2d(-11, -11, Math.toRadians(90)))
//                                //ZONE1
//                                //.lineToSplineHeading(new Pose2d(-34, -11, Math.toRadians(90)))
//                                //ZONE2
//                                //.lineToSplineHeading(new Pose2d(-57, -11, Math.toRadians(90)))
//                                //ZONE3


                                .build()



                );  //linetosplineheading
                   // splinetoheading
                  //splineToConstantHeading

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}