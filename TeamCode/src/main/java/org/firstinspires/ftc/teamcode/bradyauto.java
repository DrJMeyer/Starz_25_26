package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This OpMode illustrates the concept of driving a path based on time.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backward for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="Robot: Auto Drive By Time", group="Robot")

public class bradyauto extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor lLauncher;
    private DcMotor rLauncher;
    //private Servo sLaunch;
    private Servo sIntake;
    private DcMotor Intake = null;
    private DcMotor lIntake = null;
    static final double FORWARD_SPEED = 0.6;
    static final double FORWARD_LAUNCH = .9;


    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize the drive system variablesfrontLeftDrive = hardwareMap.get(DcMotor.class, "fpd");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "fpd");
        backLeftDrive = hardwareMap.get(DcMotor.class, "bpd");
        frontRightDrive = hardwareMap.get(DcMotor.class, "fsd");
        backRightDrive = hardwareMap.get(DcMotor.class, "bsd");
        lLauncher = hardwareMap.get(DcMotor.class, "lL");
        rLauncher = hardwareMap.get(DcMotor.class, "rL");
        //sLaunch = hardwareMap.get(Servo.class, "sup");
        sIntake = hardwareMap.get(Servo.class, "dog");

        Intake = hardwareMap.get(DcMotor.class, "inR" );
        lIntake = hardwareMap.get(DcMotor.class, "inL");
        sIntake.setPosition(0.2);
        Intake.setPower(-1);
        lIntake.setPower(-1);
        sleep(500);
        Intake.setPower(0);
        lIntake.setPower(0);
        runtime.reset();
        waitForStart();

        Intake.setPower(-1);
        lIntake.setPower(-1);
        lLauncher.setPower(-FORWARD_LAUNCH);
        rLauncher.setPower(FORWARD_LAUNCH);
        //sLaunch.setPosition(.5);
        sleep(1000);
        sIntake.setPosition(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 4)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        lLauncher.setPower(0);
        rLauncher.setPower(0);
        Intake.setPower(0);
        lIntake.setPower(0);
        //sLaunch.setPosition(0);
        sIntake.setPosition(1);
        frontLeftDrive.setPower(FORWARD_SPEED);
        frontRightDrive.setPower(-FORWARD_SPEED);
        backLeftDrive.setPower(FORWARD_SPEED);
        backRightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 4)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}