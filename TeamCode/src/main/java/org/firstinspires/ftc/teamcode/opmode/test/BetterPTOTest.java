
package org.firstinspires.ftc.teamcode.opmode.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualExtensionInCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualExtensionOutCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualLiftDownCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualLiftUpCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.Bot;

@TeleOp(name = "Better PTO Test", group = "Test")
public class BetterPTOTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Bot bot = new Bot(telemetry, hardwareMap);
        Intake intake = bot.intake;
        MecanumDrivetrain drivetrain = bot.drivetrain;
        DroneLauncher launcher = bot.launcher;
        Deposit deposit = bot.deposit;
        GamepadEx driver = new GamepadEx(gamepad1);

        waitForStart();
        while (opModeIsActive()) {
            CommandScheduler s = CommandScheduler.getInstance();

            driver.readButtons();

            if (driver.isDown(GamepadKeys.Button.B)) {
                s.schedule(new ManualExtensionOutCommand(bot.pto, 0.5));
            }
            if (driver.isDown(GamepadKeys.Button.X)) {
                s.schedule(new ManualExtensionInCommand(bot.pto, 0.5));
            }
            if (driver.isDown(GamepadKeys.Button.A)) {
                s.schedule(new ManualLiftDownCommand(bot.pto, 0.5));
            }
            if (driver.isDown(GamepadKeys.Button.Y)) {
                s.schedule(new ManualLiftUpCommand(bot.pto, 0.5));
            }

            CommandScheduler.getInstance().run();

            telemetry.addData("Left Motor Position", bot.pto.leftMotor.getCurrentPosition());
            telemetry.addData("Right Motor Position", bot.pto.rightMotor.getCurrentPosition());
            telemetry.addData("Left Motor Target", bot.pto.leftMotor.getTargetPosition());
            telemetry.addData("Right Motor Target", bot.pto.rightMotor.getTargetPosition());
            telemetry.addData("Left Motor Power", bot.pto.leftMotor.getPower());
            telemetry.addData("Right Motor Power", bot.pto.rightMotor.getPower());
            telemetry.addData("Left Motor Busy", bot.pto.leftMotor.isBusy());
            telemetry.addData("Right Motor Busy", bot.pto.rightMotor.isBusy());

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}