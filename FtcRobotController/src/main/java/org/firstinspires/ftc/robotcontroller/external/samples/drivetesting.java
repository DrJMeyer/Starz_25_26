package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class drivetesting extends LinearOpMode {
    private DcMotor robotfpd = null;
    private DcMotor robotbpd = null;

    private DcMotor robotfsd = null;
    private DcMotor robotbsd = null;

    double drivePower = 1;
    double speed = 1;
    double speedStrafe = 0.7;
    boolean isMoving;
    double startHeading;

    @Override
    public void runOpMode() {
        robotfpd = hardwareMap.get(DcMotor.class, "fpd");
        robotbpd = hardwareMap.get(DcMotor.class, "bpd");
        robotfsd = hardwareMap.get(DcMotor.class, "fsd");
        robotbsd = hardwareMap.get(DcMotor.class, "bsd");

        robotfpd.setDirection(DcMotor.Direction.FORWARD);
        robotbpd.setDirection(DcMotor.Direction.FORWARD);
        robotfsd.setDirection(DcMotor.Direction.REVERSE);
        robotbsd.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Intialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.left_stick_x > 0.2 || gamepad1.left_stick_x < -0.2 || gamepad1.left_stick_y > 0.2 ||gamepad1.left_stick_y < -0.2){
                while(gamepad1.left_stick_x > 0.2 || gamepad1.left_stick_x < -0.2 || gamepad1.left_stick_y > 0.2 ||gamepad1.left_stick_y < -0.2) {
                    robotfpd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
                    robotbpd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * drivePower);
                    robotfsd.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
                    robotbsd.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * drivePower);
                }
                robotfpd.setPower(0);
                robotbpd.setPower(0);
                robotfsd.setPower(0);
                robotbsd.setPower(0);
            } else if(gamepad1.right_stick_x > 0.15 || gamepad1.right_stick_x < -0.15&& gamepad1.left_stick_y==0 &&  gamepad1.left_stick_x==0) {
                while (gamepad1.right_stick_x > 0.15 || gamepad1.right_stick_x < -0.15 && gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0) {
                    turn();
                }
                robotfpd.setPower(0);
                robotbpd.setPower(0);
                robotfsd.setPower(0);
                robotbsd.setPower(0);
            } else {
                robotfpd.setPower(0);
                robotbpd.setPower(0);
                robotfsd.setPower(0);
                robotbsd.setPower(0);
            }

        }
    }

    public void drive() {
        if (gamepad1.left_stick_x > 0.2 && gamepad1.right_stick_y < 0.2 && gamepad1.right_stick_y > -0.2) {
            robotbpd.setPower(-(speedStrafe * (gamepad1.left_stick_x + (gamepad1.right_stick_x * .8))));
            robotfpd.setPower(-(speedStrafe * (gamepad1.left_stick_x - (gamepad1.right_stick_x * .8))));
            robotbsd.setPower(speedStrafe * (gamepad1.left_stick_x - (gamepad1.right_stick_x * .8)));
            robotfsd.setPower(speedStrafe * (gamepad1.left_stick_x + (gamepad1.right_stick_x * .8)));
        } else if (gamepad1.left_stick_x < -0.2 && gamepad1.right_stick_y < 0.2 && gamepad1.right_stick_y > -0.2) {
            robotbpd.setPower(-(speedStrafe * (gamepad1.left_stick_x + (gamepad1.right_stick_x * .8))));
            robotfpd.setPower(-(speedStrafe * (gamepad1.left_stick_x - (gamepad1.right_stick_x * .8))));
            robotbsd.setPower(speedStrafe * (gamepad1.left_stick_x - (gamepad1.right_stick_x * .8)));
            robotfsd.setPower(speedStrafe * (gamepad1.left_stick_x + (gamepad1.right_stick_x * .8)));

        } else if (gamepad1.left_stick_y > 0.2) {
            robotfpd.setPower(gamepad1.left_stick_y - (gamepad1.right_stick_x * .8));
            robotbsd.setPower(-gamepad1.left_stick_y - (gamepad1.right_stick_x * .8));
            robotfsd.setPower(gamepad1.left_stick_y + (gamepad1.right_stick_x * .8));
            robotbpd.setPower(-gamepad1.left_stick_y + (gamepad1.right_stick_x * .8));
        } else if (gamepad1.left_stick_y < -0.2) {
            robotfpd.setPower(gamepad1.left_stick_y - (gamepad1.right_stick_x * .8));
            robotbsd.setPower(-gamepad1.left_stick_y - (gamepad1.right_stick_x * .8));//(gamepad1.right_stick_x < 0.2 && gamepad1.right_stick_x > -0.2)
            robotfsd.setPower(gamepad1.left_stick_y + (gamepad1.right_stick_x * .8));
            robotbpd.setPower(-gamepad1.left_stick_y + (gamepad1.right_stick_x * .8));

        } else {
            robotfpd.setPower(0);
            robotbpd.setPower(0);
            robotfsd.setPower(0);
            robotbsd.setPower(0);
        }


    }
    public void turn(){
        if(gamepad1.right_stick_x > 0.15 || gamepad1.right_stick_x < -0.15 && gamepad1.left_stick_y==0 &&  gamepad1.left_stick_x==0){
            robotfpd.setPower(-gamepad1.right_stick_x * speed);
            robotbpd.setPower(gamepad1.right_stick_x * speed);
            robotfsd.setPower(gamepad1.right_stick_x * speed);
            robotbsd.setPower(-gamepad1.right_stick_x * speed);
        } else {
            robotfpd.setPower(0);
            robotbpd.setPower(0);
            robotfsd.setPower(0);
            robotbsd.setPower(0);
        }
    }
}
