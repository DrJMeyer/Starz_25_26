package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name = "Meowlaunch", group = "launch code")
//@Disabled
public class meowlaunch extends LinearOpMode {
    private DcMotor meowtarRight = null;
    private DcMotor meowtarLeft = null;
    private Servo meowvroLaunch = null;

    @Override
    public void runOpMode() {
        meowtarLeft = hardwareMap.get(DcMotor.class, "mL");
        meowtarRight = hardwareMap.get(DcMotor.class, "mR");
        meowvroLaunch = hardwareMap.get(Servo.class, "mLS ");
        meowtarLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        meowtarRight.setDirection(DcMotorSimple.Direction.REVERSE);
        meowvroLaunch.setDirection(Servo.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.a)
            {
                meowtarLeft.setPower(.5);
                meowtarRight.setPower(.5);
                meowvroLaunch.setPosition(.5);
            }
            else
            {
                meowtarLeft.setPower(0);
                meowtarRight.setPower(0);
                meowvroLaunch.setPosition(0);

            telemetry.addData("Servo", meowvroLaunch.getPosition());
            telemetry.update();

            }
        }
    }
}
