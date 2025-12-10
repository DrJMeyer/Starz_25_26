package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import android.annotation.SuppressLint;


import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

// This is the latest update from 11/7

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// In the future work on the main runOPmode() function in this class
// but any additional functions should be drafted in their own separate class
// and then copy pasted in here once you know you have the latest version of the code.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

@TeleOp
public class activation extends LinearOpMode {

    // Initialize all of our variables here
    private DcMotor robotfpd = null;
    private DcMotor robotbpd = null;

    private DcMotor robotfsd = null;
    private DcMotor robotbsd = null;
    private Servo sLaunch;
    private Servo sIntake;
    private DcMotor Intake = null;
    private DcMotor lIntake = null;
    private DcMotor lLauncher;
    private DcMotor rLauncher;
    private Servo megabeam;
    private Servo launchdoor;
    private NormalizedColorSensor colorSensor;
    private static final boolean USE_WEBCAM = true;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    private int[] motif;
    public int IDnum;
    public double intakePos;
    public int num;
    public double z;
    public double y;
    public boolean aPrev;
    public boolean bPrev;
    public boolean yPrev;
    public boolean lbPrev;
    public boolean aCurrent;
    public boolean bCurrent;
    public  boolean yCurrent;
    public boolean lbCurrent;
    public  boolean launcherRunning;


    // The main program begins here.

    /// // runOpMode() is called at the beginning of the LinearOpMode class we are running
    @Override
    public void runOpMode() {
        // Initialize all of our hardware variables by connecting script variables
        // with hardware configuration established on the control hub.

        // 4 driving motors
        robotfpd = hardwareMap.get(DcMotor.class, "fpd");
        robotbpd = hardwareMap.get(DcMotor.class, "bpd");
        robotfsd = hardwareMap.get(DcMotor.class, "fsd");
        robotbsd = hardwareMap.get(DcMotor.class, "bsd");

        //launch motors and intaek
        lLauncher = hardwareMap.get(DcMotor.class, "lL");
        rLauncher = hardwareMap.get(DcMotor.class, "rL");
        Intake = hardwareMap.get(DcMotor.class, "inR" );
        lIntake = hardwareMap.get(DcMotor.class, "inL");


        // a servo. Which one?
        sLaunch = hardwareMap.get(Servo.class, "sup");
        sIntake = hardwareMap.get(Servo.class, "dog");

        // color sensor (inside circular magazine)
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color");
        //intakePos
        intakePos=0;
        num=0;
        z = 0;
        y=0;
        aCurrent = false;
        bCurrent = false;
        yCurrent = false;
        lbCurrent = false;
        aPrev = false;
        yPrev = false;
        lbPrev = false;
        launcherRunning = false;


        // Still need:
        //// 1 webcam

        //initAprilTag();


        // Set direction of drive motors
        robotfpd.setDirection(DcMotor.Direction.FORWARD);
        robotbpd.setDirection(DcMotor.Direction.FORWARD);
        robotfsd.setDirection(DcMotor.Direction.REVERSE);
        robotbsd.setDirection(DcMotor.Direction.REVERSE);
        Intake.setDirection(DcMotor.Direction.FORWARD);
        lLauncher.setDirection(DcMotor.Direction.REVERSE);
        rLauncher.setDirection(DcMotor.Direction.FORWARD);
        lIntake.setDirection(DcMotor.Direction.FORWARD);

        // This is where we want to read the april tag and determine ball order.

        //IDnum = readAprilID();

        ///// create an array (like a list) of the order you need to match for balls
        // you will hold onto that array and reference it later in code which will decide which ball to shoot.
        if (IDnum == 21)
        {
            motif[0]=0;
            motif[1]=1;
            motif[2]=1;
        }
        else if (IDnum == 22)
        {
            motif[0]=1;
            motif[1]=0;
            motif[2]=1;
        }
        else if (IDnum == 23)
        {
            motif[0]=1;
            motif[1]=1;
            motif[2]=0;
        }

        // Send note to driver hub that everything looks good.
        telemetry.addData("Status", "Intialized");
        telemetry.update();

        // Program hangs out until play button is pressed
        waitForStart();

        sIntake.setPosition(0);

        // Robot will continually move through this list of code until stop button is pressed on driver hub
        while (opModeIsActive()) {

           if(gamepad1.left_stick_x > 0 || gamepad1.left_stick_x < 0 || gamepad1.left_stick_y > 0 ||gamepad1.left_stick_y < -0 || gamepad1.right_stick_x > 0 || gamepad1.right_stick_x < -0){
               moverobot();
           } else {
               nomove();
           }

            aCurrent = gamepad1.a;
            bCurrent = gamepad1.b;
            yCurrent = gamepad1.y;
            lbCurrent = gamepad1.left_bumper;

           if (aCurrent != aPrev || bCurrent != bPrev || lbCurrent != lbPrev || yCurrent != yPrev) {
               intakecode();
           }

            aPrev = aCurrent;
            bPrev = bCurrent;
            yPrev = yCurrent;
            lbPrev = lbCurrent;





            // First code checks if any gamepad is inputting a motion control (joystick activation)


            // Intake code. Also activated by the press of a button and with a deactivation mechanism.
            //// This may also need to include a method of rotating the magazine as each ball is gathered.
            // You will want a color sort and a launch code here. Likely connected functions activated by the press of a button

           // telemetryAprilTag();
            telemetry.update();

            if (gamepad1.dpad_down) {
                visionPortal.stopStreaming();
            } else if (gamepad1.dpad_up) {
                visionPortal.resumeStreaming();
            }

            sleep(20);

        }
        visionPortal.close();

    }

    private void intakecode() {
        aCurrent = gamepad1.a;
        bCurrent = gamepad1.b;
        yCurrent = gamepad1.y;
        lbCurrent = gamepad1.left_bumper;





        if (lbCurrent && !lbPrev){
            intakePos = 1.;
            sIntake.setPosition(intakePos);

        }
        if (aCurrent && !aPrev){
            intakePos = intakePos + 1./14.5;
            sIntake.setPosition(intakePos);
            z = z +1;
            telemetry.addData("Servo", sIntake.getPosition());
            telemetry.addData("expected: ", intakePos);




        }

        if (bCurrent && !bPrev){

            intakePos= intakePos - 1./14.5;
            sIntake.setPosition(intakePos);
            y= y + 1;
            telemetry.addData("Servo", sIntake.getPosition());
            telemetry.addData("expected: ", intakePos);

        }

        if (yCurrent && !yPrev){
            launcherRunning = !launcherRunning;
        }

        if (launcherRunning) {
            rLauncher.setPower(.75);
            lLauncher.setPower(.75);
        } else  {
            rLauncher.setPower(0);
            lLauncher.setPower(0);
        }


        intakePos = Range.clip(intakePos, 0.0, 1.0);
        if(intakePos >= 1){
            intakePos = 0;
            sIntake.setPosition(intakePos);
        }


    }


    private void moverobot() {

        // This function looks great. Should be ready to test
        double axial = gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = -gamepad1.right_stick_x;
        double frontLeftPower = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;
        if (gamepad1.x){
            Intake.setPower(-1);
            lIntake.setPower(-1);

        } else {
            Intake.setPower(0);
            lIntake.setPower(0);
        }

        robotfpd.setPower(frontLeftPower);
        robotfsd.setPower(frontRightPower);
        robotbpd.setPower(backLeftPower);
        robotbsd.setPower(backRightPower);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.update();
    }


    private void nomove(){
        robotbsd.setPower(0);
        robotbpd.setPower(0);
        robotfsd.setPower(0);
        robotfpd.setPower(0);
        lLauncher.setPower(0);
        rLauncher.setPower(0);
        Intake.setPower(0);
        lIntake.setPower(0);
    }
    private float checkgreen(){
        colorSensor.setGain(16);
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.green;
    }
    private float checkblue() {
        // Check in with Brady on this code. It will be merged with his.
        telemetry.addData("Gain", 16);
        colorSensor.setGain(16);
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        telemetry.addLine()
                .addData("Blue", "%.3f", colors.blue);
        return colors.blue;
    }



    private void launchCode(){

        for(int i = 0; i <= 2; i ++){
            float blue = checkblue();
            float green = checkgreen();
            if (blue >= .8 && motif[num] == 1) {
                // a filler position until we test to see what opens the flap best
                sLaunch.setPosition(1);
                // test and if needed add a wait function
                intakePos = intakePos + 1. / 3.;
                //change how much needs to be added if it does not revolve one slot
                sleep(1000);
                sIntake.setPosition(intakePos);
                num++;
                i = 2;
                sleep(2000);
                if (gamepad1.left_trigger == 1.0) ;
                {
                    lLauncher.setPower(.8);
                    rLauncher.setPower(.8);
                    // put it back to orginal position
                    sLaunch.setPosition(0);
                }


            } if (green > .9 && motif[num] == 0) {
                sLaunch.setPosition(1);
                intakePos= intakePos + 1./3.;
                sleep(1000);
                sIntake.setPosition(intakePos);
                num ++;
                i = 2;
                sleep(2000);
                if (gamepad1.right_trigger == 1.0);{
                    lLauncher.setPower(.8);
                    rLauncher.setPower(.8);
                    sLaunch.setPosition(0);
                }
            } else {
                intakePos= intakePos + 1./3.;
                sIntake.setPosition(intakePos);
            }
        }
        // Loop through the motif list each time matching a ball in the magazine to the correct color.
    }



    private void initAprilTag() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        if (USE_WEBCAM) {
            //visionPortal = VisionPortal.easyCreateWithDefaults(
                   //hardwareMap.get(WebcamName.class, "camera"), aprilTag);


        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);


        }
    }

    private int readAprilID() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));

            }


            return detection.id;

        }

        return 0;
    }


    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }
}
