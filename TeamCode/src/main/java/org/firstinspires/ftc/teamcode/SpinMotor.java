package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp()
//@Disabled
public class SpinMotor extends LinearOpMode {
    // Declare OpMode members.
    private DcMotor launcher = null;

    @Override
    public void runOpMode() {



        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize hardware variables after program mode initialized

        launcher = hardwareMap.get(DcMotor.class, "launcher");

        // Set motor to turn in correct direction
        //// Note that some of our motors will be mounted in reverse!
        launcher.setDirection(DcMotor.Direction.FORWARD);

        // Wait for game to start (driver presses START)
        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            // Variable to fill with power of the motor.
            double launchPower;

            // Set motor power using gamepad.
            launchPower = -gamepad1.left_stick_y;

            // Send power to motor
            launcher.setPower(launchPower);

            // Display motor power
            telemetry.addData("Status", "Launch Motor (%.2f)", launchPower);
            telemetry.update();



        }

        //















    }





}
