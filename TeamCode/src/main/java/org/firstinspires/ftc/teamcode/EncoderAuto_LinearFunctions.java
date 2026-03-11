package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;

@Autonomous(name="Encoder Functions", group="Robot")

public class EncoderAuto_LinearFunctions extends LinearOpMode {
    private DcMotorEx FPD = null;
    private DcMotorEx FSD = null;
    private DcMotorEx BPD = null;
    private DcMotorEx BSD = null;


    private ElapsedTime runtime = new ElapsedTime();

    static final double     PI  =   3.1415;
    static final double     R2  =   Math.sqrt(2.);
    static final double     Rwidth = 12.25;
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
        FPD = hardwareMap.get(DcMotorEx.class, "fpd");
        FSD = hardwareMap.get(DcMotorEx.class, "fsd");
        BPD = hardwareMap.get(DcMotorEx.class, "bpd");
        BSD = hardwareMap.get(DcMotorEx.class, "bsd");

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
       // DRIVE(DRIVE_SPEED, "F", 12);
        //DRIVE(DRIVE_SPEED, "B", 12);

DRIVE("F", 50, 7);
STRAFE("R", 15, 3);
STRAFE("L", 15, 5);
ZOOM("FR",56, 90, 10);



        
        /*ZOOM("FL", 12, 10);
        ZOOM("BL", 12, 10);
       /*DRIVE(DRIVE_SPEED, "F", 12);
         PIVOT(TURN_SPEED, "R", 90.0);

        DRIVE(DRIVE_SPEED, "F", 30);
        DRIVE(DRIVE_SPEED, "R", 35);
        STRAFE(DRIVE_SPEED, "L", 20);

        PIVOT(DRIVE_SPEED, "L", 45); */



        telemetry.addData("Auto","Complete");
    } //I want Zoom to be a turn while moving somewhere code, I think it may work, I don't really know though.

    // Set the target time rather than speed and compute velocity for each set of wheels in this function.
    // We will adjust setPower() in the MOVE function to setVelocity() and take a few more inputs.
    // Also we will check if java allows us to overload functions.
    //
    public void ZOOM ( String direction, double dist, double angle, double time) { //double angle removed for present use case
        int LTarget = 0;
        int RTarget = 0;
        double LSpeed = 0;
        double RSpeed = 0;

        double radangle = (angle) *(PI/180);
        double theta = (PI/2) - (radangle);
        double x = (radangle + (PI/2))/2;
        double radius = (dist/(Math.sin(theta)))*(Math.sin(x));

        double arcIN = (theta) * (radius - (Rwidth / 2.));
        double arcOUT = (theta) * (radius + (Rwidth / 2.));

        if (angle == 90) {
             arcIN = (PI / 2) * ((dist / R2) - (Rwidth / 2.));
             arcOUT = (PI / 2) * ((dist / R2) - (Rwidth / 2.));
        }





        double arcINspd =  arcIN/time ;

        double arcOUTspd = arcOUT /time ;





        if (opModeIsActive()) {
            if (direction.equals("FL")) { //forward left
                LTarget=(int) ( arcIN * COUNTS_PER_INCH );

                LSpeed=(double)(arcINspd *COUNTS_PER_INCH);



                RTarget=(int) ( arcOUT * COUNTS_PER_INCH );

                RSpeed=(double)(arcOUTspd *COUNTS_PER_INCH);



            }
            else if (direction.equals("FR")) { //forward right
                LTarget=(int) ( arcOUT * COUNTS_PER_INCH );

                LSpeed=(arcOUTspd *COUNTS_PER_INCH);

                RTarget=(int) ( arcIN * COUNTS_PER_INCH );

                RSpeed=(arcINspd *COUNTS_PER_INCH);


            }
            else if (direction.equals("BL")) {//backward left
                LTarget=(int) ( -1 * arcOUT * COUNTS_PER_INCH );

                LSpeed=(double)(-1 *(arcOUTspd *COUNTS_PER_INCH));


                RTarget=(int) ( -1 * arcIN * COUNTS_PER_INCH );
                RSpeed=(double)(-1 *(arcINspd *COUNTS_PER_INCH));


            }
            else if (direction.equals("BR")) { //backward right
                LTarget=(int) ( -1 * arcIN * COUNTS_PER_INCH );
                LSpeed=(double)(-1 *(arcINspd *COUNTS_PER_INCH));


                RTarget=(int) ( -1 * arcOUT * COUNTS_PER_INCH );
                RSpeed=(double)(-1 *(arcOUTspd *COUNTS_PER_INCH));

            }
            else {
                telemetry.addData("ERROR: INCORRECT DIRECTION", direction);
                telemetry.update();
                return;

            }

            telemetry.addData("%7d", LTarget);
            telemetry.addData("%7d", RTarget);
            telemetry.addData("%7d", LSpeed);
            telemetry.addData("%7d", RSpeed);





            MOVE(LSpeed, RSpeed, "ZOOM", direction, LTarget, RTarget, LTarget, RTarget );



        }
    }

    public void DRIVE( String direction, double dist, double time ) {

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

        double speed = CTtarget/time;

        MOVE(speed, speed, "DRIVE", direction, CTtarget, CTtarget, CTtarget, CTtarget);

    }

    public void STRAFE( String direction, double dist, double time ) {

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

        double speed = Math.abs(FStarget / time);

        MOVE(speed, speed, "STRAFE", direction, FStarget, BStarget, BStarget, FStarget);

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

        MOVE(speed,"PIVOT", direction, Ptarget, Starget, Ptarget, Starget);

    }

    public void MOVE( double Lspeed, double Rspeed, String MVmotion, String MVdir, int FPDinst, int FSDinst, int BPDinst, int BSDinst) {

        // Pass target position to motor controller
        FPD.setTargetPosition(FPD.getCurrentPosition() + FPDinst);
        FSD.setTargetPosition(FSD.getCurrentPosition() + FSDinst);
        BPD.setTargetPosition(BPD.getCurrentPosition() + BPDinst);
        BSD.setTargetPosition(BSD.getCurrentPosition() + BSDinst);

        FPD.setVelocity(Lspeed);
        FSD.setVelocity(Rspeed);
        BPD.setVelocity(Lspeed);
        BSD.setVelocity(Rspeed);

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

    public void MOVE( double speed, String MVmotion, String MVdir, int FPDinst, int FSDinst, int BPDinst, int BSDinst) {

        // Pass target position to motor controller
        FPD.setTargetPosition(FPD.getCurrentPosition() + FPDinst);
        FSD.setTargetPosition(FSD.getCurrentPosition() + FSDinst);
        BPD.setTargetPosition(BPD.getCurrentPosition() + BPDinst);
        BSD.setTargetPosition(BSD.getCurrentPosition() + BSDinst);

        FPD.setPower(speed);
        FSD.setPower(speed);
        BPD.setPower(speed);
        BSD.setPower(speed);

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