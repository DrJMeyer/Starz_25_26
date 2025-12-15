package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class magtest extends LinearOpMode {
    private Servo sIntake;
    public double intakePos;


    @Override
    public void runOpMode() {
        sIntake = hardwareMap.get(Servo.class, "sup");

        intakePos = 0;

        waitForStart();
        while(opModeIsActive()){

        }


    }



}
