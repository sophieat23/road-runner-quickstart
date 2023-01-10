package org.firstinspires.ftc.teamcode.thunderstone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Disabled
@TeleOp

public class LiftTestingDuplicate extends LinearOpMode {
    
    double slow = .5;
    double y = 0;
    double equationY = 0;
    double x = 0;
    double equationX = 0;

   private CRServo leftServo;
   private CRServo rightServo;
   
   private DcMotorEx backRight;
   private DcMotorEx backLeft;
   private DcMotorEx frontLeft;
   private DcMotorEx frontRight;
   
   private DcMotorEx lift;
   
    private double BRMotor;
    private double BLMotor;
    private double FRMotor;
    private double FLMotor;
    
    public double pow(double base, double power){
        
        double n = 1;
        
        for(int i = 0; i < power; i++){
            n *= base;
        }
        if(power == 0){
            n = 1; //already assigned this, redundant
        }
        return n;
    }

   
  @Override
    public void runOpMode() {
    
        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        
        lift = hardwareMap.get(DcMotorEx.class, "DR4B");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        
        BLMotor = 0;
        BRMotor = 0;
        FLMotor = 0;
        FRMotor = 0;

        telemetry.addData("left Servo Pos", leftServo.getPower());
        telemetry.addData("right Servo Pos", rightServo.getPower());
        
        int h = lift.getCurrentPosition();
        lift.setTargetPosition(h);



        telemetry.update();
        
        
        waitForStart();
    
             telemetry.addData("left Servo Pos", leftServo.getPower());
            telemetry.addData("right Servo Pos", rightServo.getPower());

           if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
            
            BLMotor = 0;
            BRMotor = 0;
            FLMotor = 0;
            FRMotor = 0;

              
            if(gamepad1.b){
                rightServo.setPower(1);
            } else if(gamepad1.x){
                rightServo.setPower(-1);
                
            } else if(gamepad1.dpad_down){
                rightServo.setPower(1);
                
            }else if(gamepad1.dpad_up){
                rightServo.setPower(-1);
                
            } else if(gamepad1.y){
                rightServo.setPower(-1);
                
            }  else if(gamepad1.a){
                rightServo.setPower(1);
                leftServo.setPower(1);
                
            } else {
                rightServo.setPower(0);
            }
            
            if(gamepad1.dpad_left){
                leftServo.setPower(1);
            } else if(gamepad1.dpad_right){
                leftServo.setPower(-1);
                
            } else if(gamepad1.dpad_down){
                leftServo.setPower(-1);
                
            }else if(gamepad1.dpad_up){
                leftServo.setPower(1);
                
            } else if(gamepad1.y){
                leftServo.setPower(-1);
            } else {
                leftServo.setPower(0);
            }
                
            y = gamepad1.left_stick_y ; // *slow
            x =  gamepad1.left_stick_x; //*slow
               
            equationY = .75 * pow(y, 3) + .25 * y;
            equationX = .75 * pow(x, 3) + .25 * x;
            
              //forward/backward
                BLMotor += -equationY * slow; 
                BRMotor += -equationY * slow;
                FLMotor += -equationY * slow;
                FRMotor += -equationY * slow;

                //strafing:
                FLMotor += equationX * slow;
                BLMotor += -equationX * slow;
                FRMotor += -equationX * slow;
                BRMotor += equationX * slow;
                
                
                //rotating w/o slow mode
                FLMotor += (gamepad1.right_stick_x)  * .5; //.5 * slow
                BLMotor += (gamepad1.right_stick_x)  * .5;
                FRMotor += (-gamepad1.right_stick_x)  * .5;
                BRMotor += (-gamepad1.right_stick_x)  * .5;
                
                backLeft.setPower(BLMotor);
                backRight.setPower(BRMotor);
                frontLeft.setPower(FLMotor);
                frontRight.setPower(FRMotor);
            
            
            
            //linear slide
                if(gamepad1.left_bumper) {
                    lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                    lift.setVelocity(600);
                    telemetry.addData("Outake Pow", lift.getPower());
                    h = lift.getCurrentPosition();
                    
                    
                } else if(gamepad1.right_bumper){
                    lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                    lift.setVelocity(-1000);
                    telemetry.addData("Outake Pow", lift.getPower());
                    h = lift.getCurrentPosition();

                } else {

                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    lift.setTargetPosition(h);
                    
                }
                    
            telemetry.addData("h", h);

            telemetry.addData("left Servo Pos", leftServo.getPower());
            telemetry.addData("right Servo Pos", rightServo.getPower());
            
            telemetry.update();

            
        }

        
   
   
    }


}


}