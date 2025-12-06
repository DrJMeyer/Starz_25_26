package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class strafe extends LinearOpMode {
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
            if (gamepad1.left_bumper) {
                while (gamepad1.left_bumper) {
                    robotfpd.setPower(-0.5);
                    robotbpd.setPower(.5);
                    robotfsd.setPower(0.5);
                    robotbsd.setPower(-0.5);
                }
                robotfpd.setPower(0);
                robotbpd.setPower(0);
                robotfsd.setPower(0);
                robotbsd.setPower(0);
            } else if (gamepad1.right_bumper) {
                while (gamepad1.right_bumper){
                    robotfpd.setPower(0.5);
                    robotbpd.setPower(-0.5);
                    robotfsd.setPower(-0.5);
                    robotbsd.setPower(0.5);
                }
                robotfpd.setPower(0);
                robotbpd.setPower(0);
                robotfsd.setPower(0);
                robotbsd.setPower(0);

            } else{
                    robotfpd.setPower(0);
                    robotbpd.setPower(0);
                    robotfsd.setPower(0);
                    robotbsd.setPower(0);
                }


        }

    }
}
