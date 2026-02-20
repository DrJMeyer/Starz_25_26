package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Encoder Test 2", group="Robot")

public class ENCODERtest2 extends LinearOpMode {
    private DcMotor robotfpd = null;
    private DcMotor robotbpd = null;

    private DcMotor robotfsd = null;
    private DcMotor robotbsd = null;
    private Servo whack;
    // private Servo sIntake;
    private DcMotor Intake = null;
    private DcMotor lIntake = null;
    private DcMotor lLauncher;
    private DcMotor rLauncher;

    private ElapsedTime runtime= new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 112*3.1415 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 3.38583 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.25;
    static final double     TURN_SPEED              = 0.25;

    static final double     ONE_REV                 = WHEEL_DIAMETER_INCHES * 3.1415;


    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        robotfpd  = hardwareMap.get(DcMotor.class, "fpd");
        robotfsd = hardwareMap.get(DcMotor.class, "fsd");
        robotbpd  = hardwareMap.get(DcMotor.class, "bpd");
        robotbsd = hardwareMap.get(DcMotor.class, "bsd");

        lLauncher = hardwareMap.get(DcMotor.class, "lL");
        rLauncher = hardwareMap.get(DcMotor.class, "rL");
        Intake = hardwareMap.get(DcMotor.class, "inR" );
        lIntake = hardwareMap.get(DcMotor.class, "inL");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        robotfpd.setDirection(DcMotor.Direction.REVERSE);
        robotbpd.setDirection(DcMotor.Direction.REVERSE);
        robotfsd.setDirection(DcMotor.Direction.FORWARD);
        robotbsd.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.REVERSE);
        lLauncher.setDirection(DcMotor.Direction.REVERSE);
        rLauncher.setDirection(DcMotor.Direction.REVERSE);
        lIntake.setDirection(DcMotor.Direction.REVERSE);

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
        encoderDrive(DRIVE_SPEED,  52,  52, 52, 52, 10.0); // Forward
        encoderDrive(TURN_SPEED, 19.5, -19.5, 19.5, -19.5, 50.0); // Turn
       // Intake.setPower(1);
        //lIntake.setPower(1);
        encoderDrive(DRIVE_SPEED,  30,  30, 30, 30, 100.0); // Strafe
        encoderDrive(DRIVE_SPEED,  -35,  -35, -35, -35, 10.0); // Forward
        encoderDrive(DRIVE_SPEED,  -20,  20, 20, -20, 100.0); // Strafe
        //lLauncher.setPower(1);
        //rLauncher.setPower(1);
        encoderDrive(TURN_SPEED, -9.5, 9.5, -9.5, 9.5, 50.0); // Turn
        sleep(2000);
        encoderDrive(TURN_SPEED, 9.5, -9.5, 9.5, -9.5, 50.0); // Turn
        encoderDrive(DRIVE_SPEED,  30,  30, 30, 30, 100.0); // Strafe



        //  encoderDrive(DRIVE_SPEED,  45,  45, 45, 45, 10.0); // Forward
        //encoderDrive(DRIVE_SPEED,  12,  -12, -12, 12, 100.0); // Strafe




        //encoderDrive(TURN_SPEED, 39, -39, 39, -39, 50.0); // Turn 180
        //encoderDrive(DRIVE_SPEED,  -12,  12, 12, -12, 100.0); // Strafe

        //telemetry.addData("Path", "Complete");
        //telemetry.update();
        sleep(1000);  // pause to display final telemetry message.
    }
    public void encoderDrive(double speed,
                             double fpdInches, double fsdInches, double bpdInches, double bsdInches,
                             double timeoutS) {
        int newfpdTarget;
        int newfsdTarget;
        int newbpdTarget;
        int newbsdTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newfpdTarget = robotfpd.getCurrentPosition() + (int)(fpdInches * COUNTS_PER_INCH);
            newfsdTarget = robotfsd.getCurrentPosition() + (int)(fsdInches * COUNTS_PER_INCH);
            newbpdTarget = robotbpd.getCurrentPosition() + (int)(bpdInches * COUNTS_PER_INCH);
            newbsdTarget = robotbsd.getCurrentPosition() + (int)(bsdInches * COUNTS_PER_INCH);
            robotfpd.setTargetPosition(newfpdTarget);
            robotfsd.setTargetPosition(newfsdTarget);
            robotbpd.setTargetPosition(newbpdTarget);
            robotbsd.setTargetPosition(newbsdTarget);

            telemetry.addData("Starting at ",  "%7d", robotfpd.getCurrentPosition() );
            telemetry.addData("Aiming at ",  "%7d", newfpdTarget );


            // Turn On RUN_TO_POSITION
            robotfpd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robotfsd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robotbpd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robotbsd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            robotfpd.setPower(Math.abs(speed));
            robotfsd.setPower(Math.abs(speed));
            robotbpd.setPower(Math.abs(speed));
            robotbsd.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robotfpd.isBusy() && robotfsd.isBusy() && robotbpd.isBusy() && robotbsd.isBusy())) {

                // Display it for the driver.
             //   telemetry.addData("Running to",  " %7d :%7d", newfpdTarget,  newfsdTarget, newbpdTarget, newbsdTarget);
             //   telemetry.addData("Currently at",  " at %7d :%7d",
             //           robotfpd.getCurrentPosition(), robotfsd.getCurrentPosition(), robotbpd.getCurrentPosition(), robotbsd.getCurrentPosition());
             //   telemetry.update();
            }

            telemetry.addData("Arrived at ", "%7d", robotfpd.getCurrentPosition() );
            telemetry.update();

            // Stop all motion;
            robotfpd.setPower(0);
            robotfsd.setPower(0);
            robotbpd.setPower(0);
            robotbsd.setPower(0);
            // Turn off RUN_TO_POSITION
          //  robotfpd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          //  robotfsd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          //  robotbpd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          //  robotbsd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //sleep(250);   // optional pause after each move.
        }
    }




}
