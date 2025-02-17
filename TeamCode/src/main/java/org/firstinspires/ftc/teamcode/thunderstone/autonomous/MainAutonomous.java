package org.firstinspires.ftc.teamcode.thunderstone.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

@Disabled
@Autonomous(name = "6010 PowerPlay Autonomous", group = "6010 Autos")
public class MainAutonomous extends LinearOpMode {
    private CRServo leftServo;
    private CRServo rightServo;
    private DcMotorEx lift;
//    private DcMotorEx lsMotor = linearSlide.getLsMotor();

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    private double BRMotor;
    private double BLMotor;
    private double FRMotor;
    private double FLMotor;

    private double DR4BMotor;

    public static double DISTANCE = 14;//is in feet
    boolean prgrmran = false; //unecesary to roadrunner but i have this so the programe only runs once later

    private AprilTagAutonLeft apriltag;

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //ATTENTION!!!
        //wrong sides rn bc auton code is reversed, need to fix once thats addressed!!
        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");

        //lift:
        lift = hardwareMap.get(DcMotorEx.class, "DR4B");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentH= lift.getCurrentPosition();
        // int previousH = lift.getCurrentPosition();
        lift.setTargetPosition(currentH);

        boolean wasHigh;
        boolean wasMed;

        //wheels
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");

        BLMotor = 0;
        BRMotor = 0;
        FLMotor = 0;
        FRMotor = 0;
        DR4BMotor = 0;

        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);

        lift.setDirection(DcMotorEx.Direction.REVERSE);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d()); //find the locations

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
                    break;

                case 7: //red side right
                    telemetry.addData("Red side right confirmed.", " ");
                    telemetry.update();
                    config = false;
                    break;

            }

        }


        //roadrunner
        SampleMecanumDrive myLocalizer = new SampleMecanumDrive(hardwareMap);
        myLocalizer.setPoseEstimate(new Pose2d(-35,-61.5, Math.toRadians(90))); //start pos//heading in rads
        //this starting point would be on the left

        Trajectory trL1 = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35.5, -7.3))
//                .lineTo(new Vector2d(-35.5, -36.3))
//                .lineToSplineHeading(new Pose2d(-30, -30, Math.toRadians(45)))
//                .lineToSplineHeading(new Pose2d(-39.1, -10, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();

        Trajectory trL2 = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -7.3, Math.toRadians(90)))
               .lineTo(new Vector2d(-35.5, -36.3))
//                .lineToSplineHeading(new Pose2d(-30, -30, Math.toRadians(45)))
//                .lineToSplineHeading(new Pose2d(-39.1, -10, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();

        Trajectory trL3 = myLocalizer.trajectoryBuilder(new Pose2d(-35.5, -36.3, Math.toRadians(90)))
                .lineToSplineHeading(new Pose2d(-30, -30, Math.toRadians(45)))
//                .lineToSplineHeading(new Pose2d(-39.1, -10, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();
        Trajectory trL4 = myLocalizer.trajectoryBuilder(new Pose2d(-30, -30, Math.toRadians(45)))
               .lineToSplineHeading(new Pose2d(-39.1, -10, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();
        Trajectory trL5 = myLocalizer.trajectoryBuilder(new Pose2d(-39.1, -10, Math.toRadians(180)))
               .lineTo(new Vector2d(-60, -12))
                .build();

        Trajectory trL6 = myLocalizer.trajectoryBuilder(new Pose2d(-60, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-10, -13))
//                .lineToSplineHeading(new Pose2d(-17, -16, Math.toRadians(225)))
//                .lineToSplineHeading(new Pose2d(-10, -13, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();

        Trajectory trL7 = myLocalizer.trajectoryBuilder(new Pose2d(-10, -13, Math.toRadians(180)))
                .lineToSplineHeading(new Pose2d(-17, -16, Math.toRadians(225)))
//                .lineToSplineHeading(new Pose2d(-10, -13, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();

        Trajectory trL8 = myLocalizer.trajectoryBuilder(new Pose2d(-17, -16, Math.toRadians(225)))
                .lineToSplineHeading(new Pose2d(-10, -13, Math.toRadians(180)))
//                .lineTo(new Vector2d(-60, -12))
                .build();

        Trajectory trL9 = myLocalizer.trajectoryBuilder(new Pose2d(-10, -13, Math.toRadians(180)))
                .lineTo(new Vector2d(-60, -12))
                .build();
        Trajectory trL10 = myLocalizer.trajectoryBuilder(new Pose2d(-60, -12, Math.toRadians(180)))
                .lineTo(new Vector2d(-59, -34))
                .build();




        Trajectory tr1 = myLocalizer.trajectoryBuilder(new Pose2d(-35,-61.5, Math.toRadians(90)))
                .lineTo(new Vector2d(-35, -7.3))
//                .forward(55)
                .build();
        Trajectory tr2 = myLocalizer.trajectoryBuilder(new Pose2d(-35, -7.3, Math.toRadians(90)))
                .lineTo(new Vector2d(-35, -36.3))
//                .back(30)
                .build();
//        Trajectory tr3 = myLocalizer.trajectoryBuilder(new Pose2d())
//                .turn(-.75,39,39)
//                .build();

        Trajectory traj1 = myLocalizer.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-34, -16., Math.toRadians(90)))
                .splineTo (new Vector2d(-29, -11), Math.toRadians(45))
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
        //while(opModeIsActive()) {
            // Make sure to call myLocalizer.update() on *every* loop
            // Increasing loop time by utilizing bulk reads and minimizing writes will increase your odometry accuracy
            myLocalizer.update();

            // Retrieve your pose
            Pose2d myPose = myLocalizer.getPoseEstimate();

            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());

            if (state == 4) { //blue side left
//                myLocalizer.followTrajectory(tr1);
//                myLocalizer.followTrajectory(tr2);
                myLocalizer.followTrajectory(trL1);
                myLocalizer.followTrajectory(trL2);
                myLocalizer.followTrajectory(trL3);
                myLocalizer.followTrajectory(trL4);
                myLocalizer.followTrajectory(trL5);
                for(int i = 0; i <2; i++){
                    myLocalizer.followTrajectory(trL6);
                    myLocalizer.followTrajectory(trL7);
                    myLocalizer.followTrajectory(trL8);
                    myLocalizer.followTrajectory(trL9);
                }
                myLocalizer.followTrajectory(trL10);





//                if (apriltag.getTagOfInterest().equals(null) || apriltag.getTagOfInterest().id == apriltag.LEFT) {
//                    myLocalizer.followTrajectory(tr1);
//                    myLocalizer.followTrajectory(tr2);
//                    // do something - traj
//                } else if (apriltag.getTagOfInterest().id == apriltag.MIDDLE) {
//                    myLocalizer.followTrajectory(tr1);
//                    myLocalizer.followTrajectory(tr2);
//                    // do something else - traj
//                } else if (apriltag.getTagOfInterest().id == apriltag.RIGHT) {
//                    myLocalizer.followTrajectory(tr1);
//                    myLocalizer.followTrajectory(tr2);
//                    // do something else - traj
//                }
            }
            if (state == 5) { //blue side right

            }
            if (state == 6) { //red side left
                myLocalizer.followTrajectory(traj1);
                myLocalizer.followTrajectory(traj2);
                myLocalizer.followTrajectory(traj3);
                myLocalizer.followTrajectory(traj4);
                myLocalizer.followTrajectory(traj5);
                myLocalizer.followTrajectory(traj6);
                myLocalizer.followTrajectory(traj7);
                if (apriltag.getTagOfInterest().equals(null) || apriltag.getTagOfInterest().id == apriltag.LEFT) {
                    // do something - traj
                    myLocalizer.followTrajectory(zone3);
                } else if (apriltag.getTagOfInterest().id == apriltag.MIDDLE) {
                    // do something else - traj
                    myLocalizer.followTrajectory(zone2);
                } else if (apriltag.getTagOfInterest().id == apriltag.RIGHT) {
                    // do something else - traj
                    myLocalizer.followTrajectory(zone1);
                }
            }
            if (state == 7) { //red side right

            }
        //}
    }
}
