package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Encoder Functions", group="Robot")

public class EncoderAuto_LinearFunctions extends LinearOpMode {
    private DcMotor FPD = null;
    private DcMotor FSD = null;
    private DcMotor BPD = null;
    private DcMotor BSD = null;


    private ElapsedTime runtime = new ElapsedTime();

    static final double     PI  =   3.1415;
    static final double     R2  =   Math.sqrt(2.);
    static final double     Rwidth = 12.75;
    static final double     COUNTS_PER_MOTOR_REV    =   112 * PI;
    static final double     DRIVE_GEAR_REDUCTION    =   1.0;
    static final double     WHEEL_DIAMETER_INCHES   =   3.38583;
    static final double     COUNTS_PER_INCH         =   (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);
    static final double     INCH_PER_DEGREE         =   19.5 / 90.0; // Experimental  value for determining pivot parameters. There is certainly a better way to approach this.
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

        FPD.setTargetPosition(0);
        FSD.setTargetPosition(0);
        BPD.setTargetPosition(0);
        BSD.setTargetPosition(0);

        FPD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FSD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BPD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BSD.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motors to move using encoders
//        FSD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        FPD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BPD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        BSD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Hold until start is pressed
        waitForStart();

        //// Fill with instructions here
        ZOOM(DRIVE_SPEED,"FL", 53);
       /* DRIVE(DRIVE_SPEED, "F", 52);
        PIVOT(TURN_SPEED, "R", 90.0);

        DRIVE(DRIVE_SPEED, "F", 30);
        DRIVE(DRIVE_SPEED, "R", 35);
        STRAFE(DRIVE_SPEED, "L", 20);

        PIVOT(DRIVE_SPEED, "L", 45); */



        telemetry.addData("Auto","Complete");
        telemetry.update();
    } //I want Zoom to be a turn while moving somewhere code, I think it may work, I don't really know though.
    public void ZOOM (double speed,String direction, double dist) { //double angle removed for present use case
        int LTarget = 0;
        int RTarget = 0;


        if (opModeIsActive()) {
            if (direction.equals("FL")) { //forward left
                LTarget=(int) ((PI/2.)*((dist/R2)+(Rwidth/2))*(COUNTS_PER_INCH));

                RTarget=(int) ((PI/2.)*((dist/R2)-(Rwidth/2))*(COUNTS_PER_INCH));


            }
            else if (direction.equals("FR")) { //forward right
                LTarget=(int) ((PI/2.)*((dist/R2)-(Rwidth/2))*(COUNTS_PER_INCH));

                RTarget=(int) ((PI/2.)*((dist/R2)+(Rwidth/2))*(COUNTS_PER_INCH));

            }
            else if (direction.equals("BL")) {//backward left
                LTarget=(int) (-1*(PI/2.)*((dist/R2)-(Rwidth/2))*(COUNTS_PER_INCH));

                RTarget=(int) (-1*(PI/2.)*((dist/R2)+(Rwidth/2))*(COUNTS_PER_INCH));



            }
            else if (direction.equals("BR")) { //backward right
                LTarget=(int) (-1*(PI/2.)*((dist/R2)+(Rwidth/2))*(COUNTS_PER_INCH));

                RTarget=(int) (-1*(PI/2.)*((dist/R2)-(Rwidth/2))*(COUNTS_PER_INCH));
            }
            else {
                telemetry.addData("ERROR: INCORRECT DIRECTION", direction);
                telemetry.update();
                return;

            }


            MOVE(speed, "ZOOM", direction, FPD.getCurrentPosition()+LTarget,FSD.getCurrentPosition()+RTarget, BPD.getCurrentPosition()+LTarget,BSD.getCurrentPosition()+RTarget );



        }
    }

    public void DRIVE( double speed, String direction, double dist ) {

        int CTtarget = 0;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            if (direction.equals("F")) {
                CTtarget = (int) (dist * COUNTS_PER_INCH);
            } else if (direction.equals("B")) {
                CTtarget = (int) (dist * COUNTS_PER_INCH) * -1;
            }
            else {
                telemetry.addData("ERROR: INCORRECT DIRECTION", direction);
                telemetry.update();
                return;
            }
        }

        MOVE(speed, "DRIVE", direction, FPD.getCurrentPosition() + CTtarget, FSD.getCurrentPosition() + CTtarget, BPD.getCurrentPosition() + CTtarget, BSD.getCurrentPosition() + CTtarget);

    }

    public void STRAFE( double speed, String direction, double dist ) {

        int FStarget = 0;//Front slash, forward slash goes upper right
        int BStarget = 0;//back slash, leans back, goes upper left

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            if (direction.equals("R")) {
                BStarget = (int) (dist * COUNTS_PER_INCH);
                FStarget = (int) (dist * COUNTS_PER_INCH) * -1;
            } else if (direction.equals("L")) {
                BStarget = (int) (dist * COUNTS_PER_INCH) * -1;
                FStarget = (int) (dist * COUNTS_PER_INCH);

            }
            else {
                telemetry.addData("ERROR: INCORRECT DIRECTION", direction);
                telemetry.update();
                return;
            }
        }

        MOVE(speed, "STRAFE", direction, FPD.getCurrentPosition() + FStarget, FSD.getCurrentPosition() + BStarget, BPD.getCurrentPosition() + BStarget, BSD.getCurrentPosition() + FStarget);

    }
    public void PIVOT( double speed, String direction, double angle ) {

        int Starget = 0;
        int Ptarget = 0;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            if (direction.equals("L")) {
                Ptarget = (int) (angle * COUNTS_PER_INCH * INCH_PER_DEGREE) * -1;
                Starget = (int) (angle * COUNTS_PER_INCH * INCH_PER_DEGREE);
            } else if (direction.equals("R")) {
                Ptarget = (int) (angle * COUNTS_PER_INCH * INCH_PER_DEGREE);
                Starget = (int) (angle * COUNTS_PER_INCH * INCH_PER_DEGREE) * -1;

            }
            else {
                telemetry.addData("ERROR: INCORRECT DIRECTION", direction);
                telemetry.update();
                return;
            }
        }

        MOVE(speed, "PIVOT", direction, FPD.getCurrentPosition() + Ptarget, FSD.getCurrentPosition() + Starget, BPD.getCurrentPosition() + Ptarget, BSD.getCurrentPosition() + Starget);

    }

    public void MOVE( double MVspeed, String MVmotion, String MVdir, int FPDinst, int FSDinst, int BPDinst, int BSDinst) {

        // Pass target position to motor controller
        FPD.setTargetPosition(FPDinst);
        FSD.setTargetPosition(FSDinst);
        BPD.setTargetPosition(BPDinst);
        BSD.setTargetPosition(BSDinst);

        FPD.setPower(MVspeed);
        FSD.setPower(MVspeed);
        BPD.setPower(MVspeed);
        BSD.setPower(MVspeed);

        while (opModeIsActive() &&
                (FPD.isBusy() || FSD.isBusy() || BPD.isBusy() || BSD.isBusy()) ) {
            telemetry.addData(MVmotion, MVdir);
            telemetry.update();
        }

        FPD.setPower(0.0);
        FSD.setPower(0.0);
        BPD.setPower(0.0);
        BSD.setPower(0.0);

    }

}