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
public class webcam extends LinearOpMode {

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

        initAprilTag();

        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

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
                if (tag != null ) {

                    current_x = tag.ftcPose.x;
                    current_y = tag.ftcPose.y;
                    current_rot = tag.ftcPose.yaw;





                    double errorX = target_x - current_x;          // left/right
                    double errorY = target_y - current_y;    // target distance = 10 inches
                    double errorYaw = target_rot - current_rot;   // rotation

                    // ⚙️ tuning values (adjust these!)
                    double kStrafe = 0.02;
                    double kForward = 0.02;
                    double kTurn = 0.01;

                    double strafe = -errorX * kStrafe;
                    double forward = -errorY * kForward;
                    double turn = -errorYaw * kTurn;

                    // 🧠 dead zones (prevents shaking)
                    if (Math.abs(errorX) < 1) strafe = 0;
                    if (Math.abs(errorY) < 1) forward = 0;
                    if (Math.abs(errorYaw) < 2) turn = 0;

                    // 🔒 clamp speeds
                    strafe = Range.clip(strafe, -0.5, 0.5);
                    forward = Range.clip(forward, -0.5, 0.5);
                    turn = Range.clip(turn, -0.4, 0.4);

                    driveRobot(forward, strafe, turn);

                } else {
                    // stop if no tag or button not pressed
                    driveRobot(0, 0, 1);
                }

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
        double frontRight = forward - strafe - turn;
        double backLeft   = forward - strafe + turn;
        double backRight  = forward + strafe - turn;

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