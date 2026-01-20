package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class servomove extends LinearOpMode {
    private Servo whack;
    public boolean aPrev;
    public boolean bPrev;
    public boolean yPrev;
    public boolean lbPrev;
    public boolean aCurrent;
    public boolean bCurrent;
    public  boolean yCurrent;
    public boolean lbCurrent;
    public  boolean launcherRunning;
    public  boolean rbCurrent;
    public boolean rbPrev;
    public boolean farRunning;



    @Override
    public void runOpMode() {
        whack = hardwareMap.get(Servo.class, "whack");
        aCurrent = false;
        bCurrent = false;
        yCurrent = false;
        lbCurrent = false;
        aPrev = false;
        yPrev = false;
        lbPrev = false;
        launcherRunning = false;
        rbCurrent = false;
        rbPrev = false;
        farRunning = false;


        telemetry.addData("Status", "Intialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            aCurrent = gamepad1.a;
            bCurrent = gamepad1.b;
            // yCurrent = gamepad1.y;
            lbCurrent = gamepad1.left_bumper;
            rbCurrent = gamepad1.right_bumper;

            // if (aCurrent != aPrev || bCurrent != bPrev || lbCurrent != lbPrev  || gamepad1.x || rbCurrent != rbPrev) {
            AR();
            //}

            aPrev = aCurrent;
            bPrev = bCurrent;
            // yPrev = yCurrent;
            lbPrev = lbCurrent;
            rbPrev = rbCurrent;
        }
    }

    private void AR(){
        if (lbCurrent && !lbPrev){
            launcherRunning = !launcherRunning;
        }
        if (rbCurrent && !rbPrev){
            farRunning = !farRunning;
        }

        if (aCurrent && !aPrev){
            whack.setPosition(0);
        }
        if (bCurrent && !bPrev){
            whack.setPosition(1);
        }

    }


}
