package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorTouch;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp()
public class mavwillmeowark extends LinearOpMode {

     private Servo bro = null;
    private DistanceSensor sense;
   private TouchSensor touch;
  private DcMotor meowtar;


    private NormalizedColorSensor eye;

    @Override
    public void runOpMode(){
        int spin = 0;
        float gain = 2;
        bro = hardwareMap.get(Servo.class, "bro");
        sense = hardwareMap.get(DistanceSensor.class,"sense");
        touch = hardwareMap.get(TouchSensor.class, "touch");
        meowtar = hardwareMap.get(DcMotor.class,"meowtar");
        eye = hardwareMap.get(NormalizedColorSensor.class, "eye");

        meowtar.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Intialized");
        telemetry.update();
        waitForStart();



        while (opModeIsActive()){


            if (spin == 0) {
                if (gamepad1.b) {
                    meowtar.setPower(1);
                    spin = 1;
                }else if(gamepad1.a){
                    meowtar.setPower(-1);
                    spin = -1;
                } else {
                    meowtar.setPower(0);
                    spin = 0;
                }
            }

            if (gamepad1.b) {
                spin = 0;
            }

            if (gamepad1.a) {

                gain += 0.005;
            } else if (gamepad1.b && gain > 1) {
                gain -= 0.005;
            }

            // Show the gain value via telemetry
            telemetry.addData("Gain", gain);

            // Tell the sensor our desired gain value (normally you would do this during initialization,
            // not during the loop)
            eye.setGain(gain);


        NormalizedRGBA color = eye.getNormalizedColors();

        telemetry.addData("Red","%.3f" ,color.red);
        telemetry.addData("Green", "%.3f",color.green);
        telemetry.addData("Blue","%.3f", color.blue);



            if (touch.isPressed()) {
                telemetry.addData("Button", "PRESSED");
            }else {
                telemetry.addData("Button", "NOT PRESSED");
            }

            //if(gamepad1.y) {
               // bro.setPosition(0);
           // } else if (gamepad1.b || gamepad1.x) {
              //  bro.setPosition(.5);

           // } else if (gamepad1.a) {
            //    bro.setPosition(1);

            }
        telemetry.addData("Servo Pot", bro.getPosition());
        telemetry.addData("Status", "Running");
        telemetry.addData("Distance(cm)", sense.getDistance(DistanceUnit.CM));
        telemetry.update();

        }

    }

