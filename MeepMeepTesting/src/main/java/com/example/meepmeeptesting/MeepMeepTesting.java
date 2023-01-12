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
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -61.5, Math.toRadians(90) ))
                                .lineToSplineHeading(new Pose2d(-34, -16., Math.toRadians(90)))
                                .splineTo(new Vector2d(-29, -5), Math.toRadians(45))
                                //^this would simulate going infornt of the

                                .splineTo(new Vector2d(-29, -5), Math.toRadians(45))
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