package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "newsight", group = "Practice")
public class webcamradu extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    // 🔧 Motors
    private DcMotorEx FPD = null;
    private DcMotorEx FSD = null;
    private DcMotorEx BPD = null;
    private DcMotorEx BSD = null;

    private double target_x;
    private double target_y;
    private double target_rot;

    private double current_x;
    private double current_y;
    private double current_rot;






    @Override
    public void runOpMode() {

        // 🔧 Initialize motors (MAKE SURE NAMES MATCH CONFIG)
        FPD = hardwareMap.get(DcMotorEx.class, "fpd");
        FSD = hardwareMap.get(DcMotorEx.class, "fsd");
        BPD = hardwareMap.get(DcMotorEx.class, "bpd");
        BSD = hardwareMap.get(DcMotorEx.class, "bsd");

        // Set wheel direction
        FPD.setDirection(DcMotor.Direction.REVERSE);
        BPD.setDirection(DcMotor.Direction.REVERSE);

        FSD.setDirection(DcMotor.Direction.FORWARD);
        BSD.setDirection(DcMotor.Direction.FORWARD);



        initAprilTag();

        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetry.addLine("WORKING");

                // 🔍 Get detections
                AprilTagDetection tag = null;
                List<AprilTagDetection> detections = aprilTag.getDetections();

                for (AprilTagDetection detection : detections) {
                    if (detection.metadata != null) {
                        tag = detection;
                        break; // use first valid tag
                    }
                }

                // 🎯 AUTO ALIGN WHEN HOLDING A BUTTON
                if (tag != null) {

                    target_x = 0;
                    target_y = 10;


                    current_x = tag.ftcPose.x;
                    current_y = tag.ftcPose.y;
                    current_rot = tag.ftcPose.yaw;
                    target_rot = 0;//set a value pleaseeeee


                    double errorX = target_x - current_x;          // left/right
                    double errorY = target_y - current_y;    // target distance = 10 inches
                    double errorYaw = target_rot - current_rot;   // rotation
                    double xTol = 0.75;     // inches
                    double yTol = 0.75;     // inches
                    double yawTol = 2.0;    // degrees
                    boolean aligned =
                            Math.abs(errorX) < xTol &&
                                    Math.abs(errorY) < yTol &&
                                    Math.abs(errorYaw) < yawTol;
                    if (aligned) {
                        driveRobot(0, 0, 0);
                        telemetry.addLine("Align Complete!");
                    }
                    else {
                        // normal controller
                        double kStrafe = 0.02;
                        double kForward = 0.02;
                        double kTurn = 0.006;
                        double strafe = errorX * kStrafe;
                        double forward = errorY * kForward;
                        double turn = errorYaw * kTurn;
                        // deadbands
                        if (Math.abs(errorX) < 0.5) strafe = 0;
                        if (Math.abs(errorY) < 0.5) forward = 0;
                        if (Math.abs(errorYaw) < 1.0) turn = 0;
                        // clips
                        strafe = Range.clip(strafe, -0.4, 0.4);
                        forward = Range.clip(forward, -0.4, 0.4);
                        turn = Range.clip(turn, -0.18, 0.18);
                        driveRobot(forward, strafe, turn);

                    }

                    // ⚙️ tuning values (adjust these!) ALLLLLL THIS CODE IS BUNNNNNNNSSSSSS

                    /*double kStrafe = 0.02;
                    double kForward = 0.02;
                    double kTurn = 0.006;

                    double strafe = errorX * kStrafe;
                    double forward = errorY * kForward;
                    double turn = errorYaw * kTurn;
                    turn = Range.clip(turn, -0.18, 0.18);//puts a hard limit around the values
                    if (Math.abs(errorYaw) < 1.0) turn = 0;//reduces jitteriness

                    // 🧠 dead zones (prevents shaking)
                    //  if (Math.abs(errorX) < 1) strafe = 0;
                    //   if (Math.abs(errorY) < 1) forward = 0;
                    //  if (Math.abs(errorYaw) < 2) turn = 0;

                    // 🔒 clamp speeds
                    //     strafe = Range.clip(strafe, -0.5, 0.5);
                    //        forward = Range.clip(forward, -0.5, 0.5);
                    //      turn = Range.clip(turn, -0.4, 0.4);

                    driveRobot(forward, strafe, turn);

                } else {
                    // stop if no tag
                    driveRobot(0, 0, .08);
                }*/

                    telemetryAprilTag();
                    telemetry.update();

                    // camera controls
                    if (gamepad1.dpad_down) {
                        visionPortal.stopStreaming();
                    } else if (gamepad1.dpad_up) {
                        visionPortal.resumeStreaming();
                    }

                    sleep(20);
                }
            }

            visionPortal.close();
            telemetry.update();
        }
    }

    private void initAprilTag() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }
    }

    // 🚗 Mecanum drive
    public void driveRobot(double forward, double strafe, double turn) {

        double frontLeft  = forward + strafe + turn;
        frontLeft=Range.clip(frontLeft, 1.0, -1.0);//clips these values
        double frontRight = forward - strafe - turn;
        frontRight=Range.clip(frontRight, 1.0, -1.0);//clips these values
        double backLeft   = forward - strafe + turn;
        backLeft=Range.clip(backLeft,1.0,-1.0);
        double backRight  = forward + strafe - turn;
        backRight=Range.clip(backRight,1.0,-1.0);

        FPD.setPower(frontLeft);
        FSD.setPower(frontRight);
        BPD.setPower(backLeft);
        BSD.setPower(backRight);
    }

    @SuppressLint("DefaultLocale")
    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f (inch)",
                        detection.ftcPose.x,
                        detection.ftcPose.y,
                        detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (deg)",
                        detection.ftcPose.pitch,
                        detection.ftcPose.roll,
                        detection.ftcPose.yaw));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
            }
        }
    }
}
