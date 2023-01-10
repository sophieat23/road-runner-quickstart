package org.firstinspires.ftc.teamcode.thunderstone.teleop;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class LinearSlide {
    private DcMotorEx lsMotor;

    DcMotorEx getLsMotor() {
        return lsMotor;
    }

    void setLsMotor(DcMotorEx motor) {
        lsMotor = motor;
    }

}