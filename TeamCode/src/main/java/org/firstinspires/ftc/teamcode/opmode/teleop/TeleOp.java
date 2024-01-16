package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositAutomaticHeightCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToHangHeightCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleLeftPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleV4BCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ManualLiftDownCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ManualLiftUpCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
//import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.RotateHeadingLockCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.ToggleFieldCentricCommand;
//import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.ToggleHeadingLockCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drone.LaunchDroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drone.ResetDroneLauncherCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ReverseIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeV4BCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToDepositStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToEndgameStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToIntakeStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToTransferStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
//import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    private Bot bot;
    private Intake intake;
    private MecanumDrivetrain drivetrain;
    private DroneLauncher launcher;
    private Deposit deposit;
    private GamepadEx driver, otherDriver;
    private int loopCount = 0, a = 0;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        bot = new Bot(telemetry, hardwareMap);
        intake = bot.intake;
        drivetrain = bot.drivetrain;
        launcher = bot.launcher;
        deposit = bot.deposit;
        driver = new GamepadEx(gamepad1);
        otherDriver = new GamepadEx(gamepad2);

        TriggerReader leftTrigger = new TriggerReader(driver, GamepadKeys.Trigger.LEFT_TRIGGER);
        TriggerReader rightTrigger = new TriggerReader(driver, GamepadKeys.Trigger.RIGHT_TRIGGER);

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Bot State", bot.getBotState());

            CommandScheduler s = CommandScheduler.getInstance();

            driver.readButtons();

            double multiplier = 1.0;

            //if (driver.wasJustPressed(GamepadKeys.Button.BACK))
            //    {s.schedule(new ToggleHeadingLockCommand(drivetrain));}
            if (otherDriver.wasJustPressed(GamepadKeys.Button.START))
                {s.schedule(new ToggleFieldCentricCommand(drivetrain));}
            if (otherDriver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) || otherDriver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER))
                {multiplier = 0.5;}

            s.schedule(new TeleOpDriveCommand(drivetrain,
                    new Vec2d(otherDriver.getLeftX(), -otherDriver.getLeftY()),
                    otherDriver.getRightX(), multiplier));

            //bot.intakeToTransferCheck();

            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT))
                {s.schedule(new ToIntakeStateCommand(bot));}
            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP))
                {s.schedule(new ToTransferStateCommand(bot));}
            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT))
                {s.schedule(new ToDepositStateCommand(bot));}
            if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN))
                {s.schedule(new ToEndgameStateCommand(bot));}

            switch(bot.getBotState()) {
                case INTAKE:
                    if (driver.wasJustPressed(GamepadKeys.Button.B))
                        {s.schedule(new ToggleIntakeV4BCommand(intake));}
                    if (driver.wasJustPressed(GamepadKeys.Button.X))
                        {s.schedule(new ToggleIntakeSpinnerCommand(intake));}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y))
                        {s.schedule(new ReverseIntakeSpinnerCommand(intake));}
                    break;
                case TRANSFER:
                    //if (driver.wasJustPressed(GamepadKeys.Button.A))
                    //    {s.schedule(new RotateHeadingLockCommand(drivetrain));}
                    break;
                case DEPOSIT:
                    if (driver.isDown(GamepadKeys.Button.A))
                        {s.schedule(new ReleasePixelsCommand(bot.deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.B))
                        {s.schedule(new DepositToggleRightPixelCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y))
                        {s.schedule(new DepositToggleV4BCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.X))
                        {s.schedule(new DepositToggleLeftPixelCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER))
                        {s.schedule(new ManualLiftDownCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER))
                        {s.schedule(new ManualLiftUpCommand(deposit));}
                    //if (driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) < 0.2 && driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) < 0.2)
                    //    {s.schedule(new DepositStopLiftCommand(deposit));}
                    break;
                case ENDGAME:
                    if (driver.wasJustPressed(GamepadKeys.Button.B)) {s.schedule(new LaunchDroneCommand(launcher));}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y)) {s.schedule(new DepositToHangHeightCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.X)) {s.schedule(new DepositToBottomPositionCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.A)) {s.schedule(new ResetDroneLauncherCommand(launcher));}
                    break;
                default:
                    break;
            }

            /*
            s.schedule(new RunLiftPIDCommand(deposit));

            if (otherDriver.isDown(GamepadKeys.Button.X)) {
                deposit.depositMotor.setPower(-0.5);
                deposit.otherDepositMotor.setPower(-0.5);
                a++;
                if (a == 2) {
                    deposit.otherDepositMotor.setPower(0.0);
                    deposit.otherDepositMotor.setPower(0.0);
                    deposit.otherDepositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    deposit.depositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    deposit.depositMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    deposit.otherDepositMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    a = 0;
                }
            }
            */

            if (loopCount == 5) {
                ArrayList<PixelColor> held = bot.intake.getPixelColors();
                switch (held.get(0)) {
                    case YELLOW:
                        gamepad1.setLedColor(255, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                    case PURPLE:
                        gamepad1.setLedColor(255, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                    case GREEN:
                        gamepad1.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                    case NONE:
                        gamepad1.setLedColor(0, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                }
                switch (held.get(1)) {
                    case YELLOW:
                        gamepad2.setLedColor(255, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                    case PURPLE:
                        gamepad2.setLedColor(255, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                    case GREEN:
                        gamepad2.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                    case NONE:
                        gamepad2.setLedColor(0, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                        break;
                }
                loopCount = 0;
            } else {
                loopCount++;
            }

            telemetry.update();
            s.run();
        }
    }
}