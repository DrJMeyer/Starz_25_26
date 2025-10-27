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
private Servo meowvroLaunch =null;
@Override
public void runOpMode() {
    meowtarLeft = hardwareMap.get(DcMotor.class, "mL");
    meowtarRight = hardwareMap.get(DcMotor.class, "mR");
    meowvroLaunch = hardwareMap.get(Servo.class, "mLS ");
    meowtarLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    meowtarRight.setDirection(DcMotorSimple.Direction.FORWARD);
    meowvroLaunch.setDirection(Servo.Direction.FORWARD);
waitForStart();
while (opModeIsActive()){
    if (gamepad1.a);
    {
        meowtarLeft.setPower(1);
        meowtarLeft.setPower(1);
        meowvroLaunch.setPosition(1);
    }
    }
}
}
