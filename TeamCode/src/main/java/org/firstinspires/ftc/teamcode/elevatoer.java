package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class elevatoer extends LinearOpMode {
    private Servo L;
    private Servo R;




    @Override
    public void runOpMode(){

        R = hardwareMap.get(Servo.class, "r");
        L = hardwareMap.get(Servo.class, "l");

       // R.setPosition(0);
        L.setPosition(1);



        waitForStart();
        while (opModeIsActive()){

            if(gamepad1.dpadLeftWasPressed()){
                L.setPosition(.35);
            }
            if (gamepad1.dpadUpWasPressed()){
                L.setPosition(.1);
            }

            if (gamepad1.dpadRightWasPressed()){
              //  R.setPosition(1);
            }

        }

}}
