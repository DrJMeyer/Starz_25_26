package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
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
public class newv1 extends LinearOpMode {

    // Initialize all of our variables here
    private DcMotor robotfpd = null;
    private DcMotor robotbpd = null;

    private DcMotor robotfsd = null;
    private DcMotor robotbsd = null;
    private Servo sIntake;
    private DcMotor Intake = null;
    private DcMotor lIntake = null;
    private DcMotor lLauncher;
    private DcMotor rLauncher;

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
        sIntake = hardwareMap.get(Servo.class, "dog");

        // color sensor (inside circular magazine)
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

            if (gamepad1.x){
                Intake.setPower(-1);
                lIntake.setPower(-1);

            } else {
                Intake.setPower(0);
                lIntake.setPower(0);
            }

            aCurrent = gamepad1.a;
            bCurrent = gamepad1.b;
            // yCurrent = gamepad1.y;
            lbCurrent = gamepad1.left_bumper;

            if (aCurrent != aPrev || bCurrent != bPrev || lbCurrent != lbPrev || yCurrent != yPrev || gamepad1.x) {
                intakecode();
            }

            aPrev = aCurrent;
            bPrev = bCurrent;
            // yPrev = yCurrent;
            lbPrev = lbCurrent;





            // First code checks if any gamepad is inputting a motion control (joystick activation)


            // Intake code. Also activated by the press of a button and with a deactivation mechanism.
            //// This may also need to include a method of rotating the magazine as each ball is gathered.
            // You will want a color sort and a launch code here. Likely connected functions activated by the press of a button

            // telemetryAprilTag();


        }


    }

    private void intakecode() {







        if (lbCurrent && !lbPrev){
            launcherRunning = !launcherRunning;
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

        yPrev = yCurrent;


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
        // lLauncher.setPower(0);
        // rLauncher.setPower(0);
        // Intake.setPower(0);
        // lIntake.setPower(0);
    }

    }





    private void launchCode(){

                if (gamepad1.left_trigger == 1.0){

                  sIntake.setPosition
                    lLauncher.setPower(.8);
                    rLauncher.setPower(.8);
                    // put it back to orginal position

                } else if ()
                    sIntake.setPosition(0);


        }


    }
        // Loop through the motif list each time matching a ball in the magazine to the correct color.
    }







}

