package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.Servo;
import android.annotation.SuppressLint;



import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


import java.util.List;

// This is the latest update from 11/7

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// In the future work on the main runOPmode() function in this class
// but any additional functions should be drafted in their own separate class
// and then copy pasted in here once you know you have the latest version of the code.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

@TeleOp
public class serovpostest extends LinearOpMode {

    // Initialize all of our variables here


    private Servo sIntake;


    public double intakePos;


    // The main program begins here.

    /// // runOpMode() is called at the beginning of the LinearOpMode class we are running
    @Override
    public void runOpMode() {


        sIntake = hardwareMap.get(Servo.class, "dog");

        intakePos = 0;


        while (opModeIsActive()) {
            intakecode();
        }


    }


    private void intakecode() {
        if (gamepad1.dpad_left) {
            intakePos = 0;
            sIntake.setPosition(0);
        }

        else if (gamepad1.dpad_right) {
            intakePos = intakePos + 1. / 3.;
            sIntake.setPosition(intakePos);
        }

    }
}