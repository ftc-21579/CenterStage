package org.firstinspires.ftc.teamcode.opmode.auto;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.centerstage.PropDetector;
import org.firstinspires.ftc.teamcode.common.centerstage.Side;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutonDelayCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.AutonParkCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.auto.PropMovementsCommand;
//import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.RunLiftPIDCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.RunLiftPIDCommand;
import org.firstinspires.ftc.teamcode.common.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.opmode.auto.Trajectories.Red;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Red Auto")
public class RedAuto extends LinearOpMode {
    Bot bot;
    Side startSide = Side.LEFT, parkingSide = Side.LEFT;
    GamepadEx driver;
    ElapsedTime timer = new ElapsedTime();
    private SampleMecanumDrive drive;

    private PropDetector propPipeline;
    private VisionPortal portal;
    private int timeCount = 0;
    
    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot(telemetry, hardwareMap);

        driver = new GamepadEx(gamepad1);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(Red.leftX, Red.leftY, Math.toRadians(270))); // TODO CHANGE TO WHAT IT SHOULD BE

        propPipeline = new PropDetector("RED");

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "DepositCam"))
                .setCameraResolution(new Size(640, 360))
                .addProcessor(propPipeline)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                //.setAutoStopLiveView(true)
                .build();

        //portal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "DepositCam"), propPipeline);
        portal.setProcessorEnabled(propPipeline, true);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        while(!isStarted()) {
            telemetry.clearAll();

            driver.readButtons();

            if(driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                startSide = Side.LEFT;
                drive.setPoseEstimate(new Pose2d(Red.leftX, Red.leftY, Math.toRadians(270)));
            } else if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                startSide = Side.RIGHT;
                drive.setPoseEstimate(new Pose2d(Red.rightX, Red.rightY, Math.toRadians(270)));
            }
            if(driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
                parkingSide = Side.LEFT;
            } else if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
                parkingSide = Side.RIGHT;
            }
            if(driver.wasJustPressed(GamepadKeys.Button.B)) {
                if(timeCount <= 14) {
                    timeCount++;
                }
            } else if (driver.wasJustPressed(GamepadKeys.Button.X)) {
                if (timeCount >= 1) {
                    timeCount--;
                }
            }
            gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);

            if (driver.wasJustPressed(GamepadKeys.Button.A)) {
                new GrabPixelsCommand(bot.deposit).execute();
            }

            telemetry.addLine("RED AUTO (dpad to change side/parking)");
            telemetry.addData("Start Side: ", startSide);
            telemetry.addData("Parking: ", parkingSide);
            telemetry.addData("Delay: ", timeCount);
            telemetry.addData("Prop: ", propPipeline.getPosition());
            telemetry.update();
        }

        PropDetector.PropPosition propPosition = propPipeline.getPosition();
        portal.close();
        //propPosition = PropDetector.PropPosition.CENTER;
        timer.reset();

        // get prop using propPosition (LEFT, RIGHT, CENTER)
        new SequentialCommandGroup(
                new AutonDelayCommand(bot, drive, timeCount),
                new PropMovementsCommand(bot, drive, propPosition, Alliance.RED, startSide),
                //new AutonCyclePixelsCommand(drive, bot, Alliance.RED)
                new AutonParkCommand(bot, drive, Alliance.RED, parkingSide)
        ).schedule();

        while (opModeIsActive()) {
            drive.update();
            new RunLiftPIDCommand(bot.deposit).schedule();
            telemetry.update();
            CommandScheduler.getInstance().run();
        }
    }
}
