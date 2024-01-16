package org.firstinspires.ftc.teamcode.opmode.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ManualLiftDownCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ManualLiftUpCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.extension.ManualExtendExtensionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.extension.ManualRetractExtensionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class BetterPTOTest extends LinearOpMode {
    private Bot bot;
    private Intake intake;
    private MecanumDrivetrain drivetrain;
    private DroneLauncher launcher;
    private Deposit deposit;

    private GamepadEx driver;
    @Override
    public void runOpMode() throws InterruptedException {

        bot = new Bot(telemetry, hardwareMap);
        intake = bot.intake;
        drivetrain = bot.drivetrain;
        launcher = bot.launcher;
        deposit = bot.deposit;
        driver = new GamepadEx(gamepad1);

        waitForStart();
        while (opModeIsActive()) {

            if (driver.wasJustPressed(GamepadKeys.Button.B)) {
                new ManualExtendExtensionCommand(bot).execute();
            }
            if (driver.wasJustPressed(GamepadKeys.Button.X)) {
                new ManualRetractExtensionCommand(bot).execute();
            }
            if (driver.wasJustPressed(GamepadKeys.Button.A)) {
                new ManualLiftDownCommand(deposit).execute();
            }
            if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
                new ManualLiftUpCommand(deposit).execute();
            }

            CommandScheduler.getInstance().run();

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
