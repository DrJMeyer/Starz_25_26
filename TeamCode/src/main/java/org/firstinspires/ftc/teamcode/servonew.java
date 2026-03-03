package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
public class servonew extends LinearOpMode {
    private Servo servo1 = null;
    private Servo servo2 = null;

    public void runOpMode(){
        servo1= hardwareMap.get(Servo.class, "serv1");
        servo2 = hardwareMap.get(Servo.class, "serv1");


while (opModeIsActive()){

    if(gamepad1.dpad_down){

    }


}

    }

}
