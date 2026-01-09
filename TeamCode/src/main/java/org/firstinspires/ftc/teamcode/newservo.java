package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name = "NewServo", group = "launch code")
//@Disabled
public class newservo extends LinearOpMode {
    private Servo meowvroLaunch = null;

    @Override
    public void runOpMode() {

        meowvroLaunch = hardwareMap.get(Servo.class, "mLS ");

        meowvroLaunch.setDirection(Servo.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.a)
            {

                meowvroLaunch.setPosition(1);
            }
            else
            {
                meowvroLaunch.setPosition(0);

                telemetry.addData("Servo", meowvroLaunch.getPosition());
                telemetry.update();
            }
        }
    }
}
