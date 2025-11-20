package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp
public class test extends LinearOpMode {
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private Servo sLaunch;
    private Servo sIntake;
    private DcMotor Intake = null;
    private DcMotor lLauncher;
    private DcMotor rLauncher;
    private Servo megabeam;
    private Servo launchdoor;
    private NormalizedColorSensor colorSensor;
    private static final boolean USE_WEBCAM = true;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    private int[] motif = {1, 2, 3,};
    public int IDnum;
    public double intakePos;
    public int num;





    @Override
    public void runOpMode() {
        lLauncher = hardwareMap.get(DcMotor.class, "lL");
        rLauncher = hardwareMap.get(DcMotor.class, "rL");
        sLaunch = hardwareMap.get(Servo.class, "sup");
        sIntake = hardwareMap.get(Servo.class, "dog");
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color");
        intakePos = 0;
        num = 0;
        IDnum = 21;

        ///// create an array (like a list) of the order you need to match for balls
        // you will hold onto that array and reference it later in code which will decide which ball to shoot.
        if (IDnum == 21) {
            motif[0] = 0;
            motif[1] = 1;
            motif[2] = 1;
        } else if (IDnum == 22) {
            motif[0] = 1;
            motif[1] = 0;
            motif[2] = 1;
        } else if (IDnum == 23) {
            motif[0] = 1;
            motif[1] = 1;
            motif[2] = 0;
        }

        // Send note to driver hub that everything looks good.
        telemetry.addData("Status", "Intialized");
        telemetry.update();

        // Program hangs out until play button is pressed
        waitForStart();

        // Robot will continually move through this list of code until stop button is pressed on driver hub
        while (opModeIsActive()) {
            launchCode();

        }
    }
    private double checkcolor () {
    // Check in with Brady on this code. It will be merged with his.
    colorSensor.setGain(16);
    NormalizedRGBA colors = colorSensor.getNormalizedColors();

    return colors.blue;
    }

private void launchCode(){

    for(int i = 0; i <= 2; i ++){
        double blue = checkcolor();

        telemetry.addData("Blue", blue);
        telemetry.addData("Motif Needed", motif[num]);
        telemetry.addData("Num Index", num);
        telemetry.addData("Pot", sLaunch.getPosition());
        telemetry.addData("Pot2", sIntake.getPosition());
        telemetry.update();

        if (blue >= .5 && motif[num] == 1){
            // a filler position until we test to see what opens the flap best
            sLaunch.setPosition(0);
            // test and if needed add a wait function( Brady : its practical do eliminate error and increase accuracy)
            intakePos= intakePos + 1./3.;
            //change how much needs to be added if it does not revolve one slot
            sIntake.setPosition(intakePos);
            num = num + 1;
            i = 2;



        }
        sleep(1000);
        if (blue < .5 && motif[num] == 0) {
            lLauncher.setPower(1);
            sleep(2000 );
            sLaunch.setPosition(1);
            intakePos= intakePos + 1./3.;
            sIntake.setPosition(intakePos);
            num = num + 1;
            i = 2;

        }

        else if (intakePos==1);
            lLauncher.setPower(0);
            rLauncher.setPower(0);
            sIntake.setPosition(0);
        }
    }
    // Loop through the motif list each time matching a ball in the magazine to the correct color.
}




