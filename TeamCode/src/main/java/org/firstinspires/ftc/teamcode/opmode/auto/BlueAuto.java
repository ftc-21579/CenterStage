package org.firstinspires.ftc.teamcode.opmode.auto;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.BluePropDetector;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Auto")
public class BlueAuto extends LinearOpMode {
    Bot bot;
    String startSide = "LEFT";
    GamepadEx driver;
    ElapsedTime timer = new ElapsedTime();

    //private BluePropDetector propPipeline;
    //private VisionPortal portal;

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot(telemetry, hardwareMap);

        driver = new GamepadEx(gamepad1);

        /*propPipeline = new BluePropDetector();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "IntakeCam"))
                .setCameraResolution(new Size(1920, 1080))
                .addProcessor(propPipeline)
                //.setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();
        */
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        while(!isStarted()) {
            telemetry.clearAll();

            driver.readButtons();

            if(driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                startSide = "BACKSTAGE";
            } else if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                startSide = "AUDIENCE";
            }

            telemetry.addLine("AUTO (dpad to change side)");
            telemetry.addData("Start Side", startSide);
            //telemetry.addData("Prop: ", propPipeline.getLocation());
            telemetry.update();
        }

        //String propLocation = propPipeline.getLocation();
        //portal.close();
        timer.reset();

        if (startSide == "BACKSTAGE") {
            while(timer.milliseconds() < 1500) {
                bot.drivetrain.teleopDrive(new Vec2d(0, -1), 0, 0.5);
            }
        } else {
            while(timer.milliseconds() < 500) {
                bot.drivetrain.teleopDrive(new Vec2d(1, 0), 0, 0.5);
            }
            while(timer.milliseconds() < 3000) {
                bot.drivetrain.teleopDrive(new Vec2d(0, -1), 0, 0.5);
            }
        }

    }
}
