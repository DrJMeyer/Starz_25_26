package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
public class servonew extends LinearOpMode {
    private Servo servo1 = null;
    private Servo servo2 = null;

    public void runOpMode() {
        servo1 = hardwareMap.get(Servo.class, "serv1");
        servo2 = hardwareMap.get(Servo.class, "serv1");


        while (opModeIsActive()) {

            if (gamepad1.dpad_down) {
                servo1.setPosition(0);
                servo2.setPosition(0);
            }
            if (gamepad1.dpad_up) {
                servo1.setPosition(.8);
                servo2.setPosition(.8);
            }
            if (gamepad1.dpad_right) {
                servo1.setPosition(1);
            }
            if (gamepad1.dpad_left) {
                servo2.setPosition(1);
            }

        }
    }
}