package org.firstinspires.ftc.teamcode.opmode.auto;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.PropDetector;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Blue Auto")
public class BlueAuto extends LinearOpMode {
    Bot bot;
    String startSide = "LEFT";
    GamepadEx driver;
    ElapsedTime timer = new ElapsedTime();

    private PropDetector propPipeline;
    private VisionPortal portal;

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot(telemetry, hardwareMap);

        driver = new GamepadEx(gamepad1);

        propPipeline = new PropDetector("BLUE");

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "DepositCam"))
                .setCameraResolution(new Size(640, 480))
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

            bot.deposit.telemCameraState();

            telemetry.addLine("AUTO (dpad to change side)");
            telemetry.addData("Start Side", startSide);
            telemetry.addData("Prop: ", propPipeline.getPosition());
            telemetry.update();
        }

        PropDetector.PropPosition propPosition = propPipeline.getPosition();
        portal.close();
        timer.reset();

        // get prop using propPosition (LEFT, RIGHT, CENTER)
        if (startSide == "LEFT") {
            // do something
        } else {
            // do something else
        }

    }
}