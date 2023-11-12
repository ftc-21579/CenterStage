package org.firstinspires.ftc.teamcode.opmode.auto;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.BluePropDetector;
import org.firstinspires.ftc.vision.VisionPortal;

public class BlueAuto extends LinearOpMode {

    String startSide = "LEFT";
    GamepadEx driver;

    private BluePropDetector propPipeline;
    private VisionPortal portal;

    @Override
    public void runOpMode() throws InterruptedException {
        driver = new GamepadEx(gamepad1);

        propPipeline = new BluePropDetector();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "IntakeCam"))
                .setCameraResolution(new Size(1920, 1080))
                .addProcessor(propPipeline)
                //.setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        while(!isStarted()) {
            telemetry.clearAll();

            driver.readButtons();

            if(driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                startSide = "LEFT";
            } else if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                startSide = "RIGHT";
            }

            telemetry.addLine("BLUE ALLIANCE AUTO (dpad to change side)");
            telemetry.addData("Start Side", startSide);
            telemetry.addData("Prop: ", propPipeline.getLocation());
            telemetry.update();
        }

        String propLocation = propPipeline.getLocation();
        portal.close();
    }
}
