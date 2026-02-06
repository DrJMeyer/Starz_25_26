package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ENCODER extends LinearOpMode {
    private DcMotor robotfpd = null;
    private DcMotor robotbpd = null;

    private DcMotor robotfsd = null;
    private DcMotor robotbsd = null;

    private ElapsedTime runtime= new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 28 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 3.38583 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;


    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        robotfpd  = hardwareMap.get(DcMotor.class, "fpd");
        robotfsd = hardwareMap.get(DcMotor.class, "fsd");
        robotbpd  = hardwareMap.get(DcMotor.class, "bpd");
        robotbsd = hardwareMap.get(DcMotor.class, "bsd");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        robotfpd.setDirection(DcMotor.Direction.FORWARD);
        robotbpd.setDirection(DcMotor.Direction.FORWARD);
        robotfsd.setDirection(DcMotor.Direction.REVERSE);
        robotbsd.setDirection(DcMotor.Direction.REVERSE);

        robotfpd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotfsd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotbpd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotbsd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robotfpd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotfsd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotbpd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotbsd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Starting at",  "%7d :%7d",
                robotfpd.getCurrentPosition(),
                robotfsd.getCurrentPosition(),
                robotbpd.getCurrentPosition(),
                robotbsd.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robotfpd.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robotfsd.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robotfpd.setTargetPosition(newLeftTarget);
            robotfsd.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftDrive.setPower(Math.abs(speed));
            rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftDrive.isBusy() && rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftDrive.setPower(0);
            rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }




}
