package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.RotateHeadingLockCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.ToggleFieldCentricCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.ToggleHeadingLockCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.UpdateLocalizerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeV4BCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToDepositStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToEndgameStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToIntakeStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToTransferStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.function.BooleanSupplier;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    Bot bot;
    Intake intake;
    DifferentialSwerveDrivetrain drivetrain;
    GamepadEx driver;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        bot = new Bot(telemetry, hardwareMap);
        intake = bot.intake;
        drivetrain = bot.drivetrain;
        driver = new GamepadEx(gamepad1);

        waitForStart();

        while(opModeIsActive()) {

            CommandScheduler s = CommandScheduler.getInstance();

            s.schedule(new UpdateLocalizerCommand(drivetrain));

            double multiplier = 1.0;

            if (driver.wasJustPressed(GamepadKeys.Button.BACK))
                {s.schedule(new ToggleHeadingLockCommand(drivetrain));}
            if (driver.wasJustPressed(GamepadKeys.Button.START))
                {s.schedule(new ToggleFieldCentricCommand(drivetrain));}
            if (driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) || driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER))
                {multiplier = 0.5;}

            s.schedule(new TeleOpDriveCommand(drivetrain,
                    new Vec2d(driver.getLeftX(), -driver.getLeftY()),
                    -driver.getRightX(), multiplier));

            bot.intakeToTransferCheck();

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
                    break;
                case TRANSFER:
                    if (driver.wasJustPressed(GamepadKeys.Button.A))
                        {s.schedule(new RotateHeadingLockCommand(drivetrain));}
                    break;
                case DEPOSIT:
                    if (driver.wasJustPressed(GamepadKeys.Button.B)) {}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y)) {}
                    if (driver.wasJustPressed(GamepadKeys.Button.X)) {}
                    break;
                case ENDGAME:
                    if (driver.wasJustPressed(GamepadKeys.Button.B)) {}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y)) {}
                    if (driver.wasJustPressed(GamepadKeys.Button.X)) {}
                    if (driver.wasJustPressed(GamepadKeys.Button.A)) {}
                    break;
                default:
                    break;
            }

            telemetry.update();
            s.run();
        }
    }
}