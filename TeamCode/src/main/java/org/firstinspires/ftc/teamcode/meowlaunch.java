package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Meowlaunch", group = "launch code")
//@Disabled
public class meowlaunch extends LinearOpMode {
private DcMotor meowtarRight= null;
private DcMotor meowtarLeft= null;
private Servo meowvroLanch =null;
@Override
public void runOpMode() {
    meowtarLeft = hardwareMap.get(DcMotor.class, "meowtarLeft");
    meowtarRight = hardwareMap.get(DcMotor.class, "meowtarRight");
    meowvroLanch = hardwareMap.get(Servo.class, "meowvroLaunch");
    meowtarLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    meowtarRight.setDirection(DcMotorSimple.Direction.FORWARD);
    meowvroLanch.setDirection(Servo.Direction.FORWARD);
waitForStart();
while (opModeIsActive()){
    if (gamepad1.a);
    {
        meowtarLeft.setPower(1);
        meowtarLeft.setPower(1);
        meowvroLanch.setPosition(1);
    }
    }
}
}
