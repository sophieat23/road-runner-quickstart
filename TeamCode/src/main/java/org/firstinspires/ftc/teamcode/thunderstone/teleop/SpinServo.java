package org.firstinspires.ftc.teamcode.thunderstone.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;





@TeleOp(name = "testing servos")
    public class SpinServo extends LinearOpMode{

    private CRServo leftServo;
    private CRServo rightServo;

    @Override
    public void runOpMode() //may add thrwos interupted exceptinon
    {
        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        waitForStart();
        while (!isStarted() && !isStopRequested())
        {
            leftServo.setPower(1);
            rightServo.setPower(1);
        }

    }
}
