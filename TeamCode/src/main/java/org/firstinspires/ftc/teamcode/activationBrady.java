package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp
public class activation extends LinearOpMode {

    // Initialize all of our variables here
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private Servo sLaunch;
    private Servo sIntake;
    private NormalizedColorSensor colorSensor;
    private static final boolean USE_WEBCAM = true;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private DcMotor lLauncher;
    private DcMotor rLauncher;

    private int[] motif;
    public int IDnum;


    // The main program begins here.

    /// // runOpMode() is called at the beginning of the LinearOpMode class we are running
    @Override
    public void runOpMode() {

        // Initialize all of our hardware variables by connecting script variables
        // with hardware configuration established on the control hub.

        // 4 driving motors
        frontLeftDrive = hardwareMap.get(DcMotor.class, "fpd");
        backLeftDrive = hardwareMap.get(DcMotor.class, "bpd");
        frontRightDrive = hardwareMap.get(DcMotor.class, "fsd");
        backRightDrive = hardwareMap.get(DcMotor.class, "bsd");
        //launch motors and
        lLauncher = hardwareMap.get(DcMotor.class, "lL");
        rLauncher = hardwareMap.get(DcMotor.class, "rL");
        // a servo. Which one?
        sLaunch = hardwareMap.get(Servo.class, "sup");
        sIntake = hardwareMap.get(Servo.class, "dog");

        // color sensor (inside circular magazine)
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color");

        // Still need:
        //// 1 more servo ( 2 total: one to open the launch flap and one to spin the mag)
        //// 1 motor - spin the intake stars
        //// 2 motors - launch motors
        //// 1 webcam

        // Set direction of drive motors
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        // This is where we want to read the april tag and determine ball order.

        IDnum = readAprilID();
        if (IDnum == 21) ;
        {
            motif[]={
            0, 1, 1
        } ;
        }
        else if (IDnum == 22) ;
        {
            motif[]={
            1, 0, 1
        } ;
        }
        else if (IDnum == 23) ;
        {
            motif[]={
            1, 1, 0
        } ;
        }


        ///// create an array (like a list) of the order you need to match for balls
        // you will hold onto that array and reference it later in code which will decide which ball to shoot.

        // Send note to driver hub that everything looks good.
        telemetry.addData("Status", "Intialized");
        telemetry.update();

        // Program hangs out until play button is pressed
        waitForStart();

        // Robot will continually move through this list of code until stop button is pressed on driver hub
        while (opModeIsActive()) {

            // First code checks if any gamepad is inputting a motion control (joystick activation)
            moverobot();

            // Intake code. Also activated by the press of a button and with a deactivation mechanism.
            //// This may also need to include a method of rotating the magazine as each ball is gathered.

            // You will want a color sort and a launch code here. Likely connected functions activated by the press of a button
            launchCode();
            {

                int IDnum;
                IDnum = readAprilID();
                while (gamepad1.left_trigger)
                    lLauncher.setPower(.5);
                rLauncher.setPower(.5);
                sLaunch.setPosition(0);
                if (IDnum == 21)
            }
            public void checkcolor;
            () {

            }
        }
    }


    private void moverobot() {

        // This function looks great. Should be ready to test
        double axial = gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;
        double frontLeftPower = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;
        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.update();
    }

    private void checkcolor() {

        // Check in with Brady on this code. It will be merged with his.
        telemetry.addData("Gain", 16);
        colorSensor.setGain(16);
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
    }

    private int readAprilID() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));

            }


            return (detection."id");

        }

    }
}


