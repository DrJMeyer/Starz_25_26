package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Encoder Functions", group="Robot")

public class EncoderAuto_Functions extends LinearOpMode {
    private DcMotor FPD = null;
    private DcMotor FSD = null;
    private DcMotor BPD = null;
    private DcMotor BSD = null;

    private int FPDtarget = 0;
    private int FSDtarget = 0;
    private int BPDtarget = 0;
    private int BSDtarget = 0;

    private ElapsedTime runtime = new ElapsedTime();

    static final double     PI  =   3.1415;
    static final double     COUNTS_PER_MOTOR_REV    =   112 * PI;
    static final double     DRIVE_GEAR_REDUCTION    =   1.0;
    static final double     WHEEL_DIAMETER_INCHES   =   3.38583;
    static final double     COUNTS_PER_INCH         =   (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);
    static final double     DRIVE_SPEED             =   0.25;
    static final double     TURN_SPEED              =   0.25;
    static final double     ONE_REV                 =   WHEEL_DIAMETER_INCHES * PI;

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void runOpMode() {

        // Initialize hardware map
        FPD = hardwareMap.get(DcMotor.class, "fpd");
        FSD = hardwareMap.get(DcMotor.class, "fsd");
        BPD = hardwareMap.get(DcMotor.class, "bpd");
        BSD = hardwareMap.get(DcMotor.class, "bsd");

        // Set wheel direction
        FPD.setDirection(DcMotor.Direction.REVERSE);
        BPD.setDirection(DcMotor.Direction.REVERSE);

        FSD.setDirection(DcMotor.Direction.FORWARD);
        BSD.setDirection(DcMotor.Direction.FORWARD);

        // Reset encoder at initialization
        FPD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FSD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BSD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BPD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to move using encoders
        FSD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FPD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BPD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BSD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Hold until start is pressed
        waitForStart();

        //// Fill with instructions here
        // encoderDrive functions here

        telemetry.addData("Auto","Complete");
        telemetry.update();
    }


    public void Drive( double speed,
                       double fpdInch, double fsdInch, double bpdInch, double bsdInch) {




    }

}
