//package org.firstinspires.ftc.teamcode.thunderstone.teleop;
//
//import com.google.blocks.ftcrobotcontroller.runtime.DcMotorAccess;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//public class Hardware {
//    private HardwareMap hwMap;
//    DcMotorEx frontLeft, frontRight, backLeft, backRight;
//    CRServo leftServo, rightServo;
//
//    public void init(HardwareMap hwMap) {
//        this.hwMap = hwMap;
//
//        //lift
//        DcMotorEx lift = hardwareMap.get(DcMotorEx.class, "DR4B");
//
//        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        //wheels
//        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
//        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
//        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
//        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
//
//        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
//        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
//
//        //intake
//        leftServo = hardwareMap.get(CRServo.class, "leftServo");
//        rightServo = hardwareMap.get(CRServo.class, "rightServo");
//
//        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
//    }
//
//
//}
