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
    private DcMotor Intake;
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
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        sLaunch = hardwareMap.get(Servo.class, "sup");
        sIntake = hardwareMap.get(Servo.class, "dog");
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color");
        intakePos = 0;
        num = 0;
        IDnum = 22;

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

            telemetry.addData("gp","HIT A BUTTON");
            sleep(2000);
            intake();

            sleep(3000);
            no();
            launchCode();
            sleep(3000);
            no();





        }
    }

    private void intake(){
        if (gamepad1.aWasPressed()){
            Intake.setPower(1);
        }
    }

    private void no(){
        lLauncher.setPower(0);
        rLauncher.setPower(0);
        Intake.setPower(0);
    }

    private float checkcolor () {
    // Check in with Brady on this code. It will be merged with his.
    colorSensor.setGain(16);
    NormalizedRGBA colors = colorSensor.getNormalizedColors();
    telemetry.addData("Blue", "%.3f", colors.blue);
    telemetry.update();
    return colors.blue;
    }

    private float green () {
        // Check in with Brady on this code. It will be merged with his.
        colorSensor.setGain(16);
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        telemetry.addData("Green", "%.3f", colors.green);
        telemetry.update();
        return colors.green;
    }

private void launchCode(){

    for(int i = 0; i <= 2; i ++){
        float blue = checkcolor();
        float green = green();

        telemetry.addData("Blue", blue);
        telemetry.addData("Green", green);
        telemetry.addData("Motif Needed", motif[num]);
        telemetry.addData("Num Index", num);
        telemetry.addData("Pot", sLaunch.getPosition());
        telemetry.addData("Pot2", sIntake.getPosition());
        telemetry.update();

        if (blue >= .8 && motif[num] == 1){
            // a filler position until we test to see what opens the flap best
            lLauncher.setPower(1);
            sLaunch.setPosition(1);
            sleep(1000);
            intakePos = intakePos + 1. / 15.;
            sIntake.setPosition(intakePos);
            sleep(3000);
                if (gamepad1.left_trigger == 1.0);{
                lLauncher.setPower(.8);
                rLauncher.setPower(.8);
                sLaunch.setPosition(0);
                }
            num = num + 1;
            i = 2;



        }
        sleep(1000);
        if (green > .9 && motif[num] == 0) {
            lLauncher.setPower(.5);
            sLaunch.setPosition(1);
            sleep(1000);
            intakePos = intakePos + 1. / 15.;
            sIntake.setPosition(intakePos);
            sleep(3000);
                if (gamepad1.left_trigger == 1.0);{
                lLauncher.setPower(.1);
                rLauncher.setPower(.8);
                sLaunch.setPosition(0);
                }
            num = num + 1;
            i = 2;

        }

        else {
            rLauncher.setPower(1);

                   }
    }
    // Loop through the motif list each time matching a ball in the magazine to the correct color.
}
}



