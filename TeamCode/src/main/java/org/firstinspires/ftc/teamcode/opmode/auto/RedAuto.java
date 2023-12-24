package org.firstinspires.ftc.teamcode.opmode.auto;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.centerstage.PropDetector;
import org.firstinspires.ftc.teamcode.common.centerstage.Side;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.PropMovementsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.RunLiftPIDCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Red Auto")
public class RedAuto extends LinearOpMode {
    Bot bot;
    Side startSide = Side.LEFT;
    GamepadEx driver;
    ElapsedTime timer = new ElapsedTime();
    private SampleMecanumDrive drive;

    private PropDetector propPipeline;
    private VisionPortal portal;

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot(telemetry, hardwareMap);

        driver = new GamepadEx(gamepad1);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(12, 64, Math.toRadians(90)));

        propPipeline = new PropDetector("RED");

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
                startSide = Side.LEFT;
            } else if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                startSide = Side.RIGHT;
            }

            telemetry.addLine("RED AUTO (dpad to change side)");
            telemetry.addData("Start Side", startSide);
            telemetry.addData("Prop: ", propPipeline.getPosition());
            telemetry.update();
        }

        PropDetector.PropPosition propPosition = propPipeline.getPosition();
        portal.close();
        propPosition = PropDetector.PropPosition.CENTER;
        timer.reset();

        // get prop using propPosition (LEFT, RIGHT, CENTER)
        new PropMovementsCommand(bot, drive, propPosition, Alliance.RED, startSide).schedule();

        while (opModeIsActive()) {
            drive.update();
            new RunLiftPIDCommand(bot.deposit).schedule();
            telemetry.update();
            CommandScheduler.getInstance().run();
        }
    }
}
