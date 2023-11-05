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

        BooleanSupplier driver_a = () -> driver.wasJustPressed(GamepadKeys.Button.A);
        BooleanSupplier driver_b = () -> driver.wasJustPressed(GamepadKeys.Button.B);
        BooleanSupplier driver_x = () -> driver.wasJustPressed(GamepadKeys.Button.X);
        BooleanSupplier driver_y = () -> driver.wasJustPressed(GamepadKeys.Button.Y);
        BooleanSupplier driver_up = () -> driver.wasJustPressed(GamepadKeys.Button.DPAD_UP);
        BooleanSupplier driver_down = () -> driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN);
        BooleanSupplier driver_left = () -> driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT);
        BooleanSupplier driver_right = () -> driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT);
        BooleanSupplier driver_back = () -> driver.wasJustPressed(GamepadKeys.Button.BACK);
        BooleanSupplier driver_start = () -> driver.wasJustPressed(GamepadKeys.Button.START);
        BooleanSupplier driver_left_bumper = () -> driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER);
        BooleanSupplier driver_right_bumper = () -> driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER);

        waitForStart();

        while(opModeIsActive()) {

            CommandScheduler s = CommandScheduler.getInstance();

            s.schedule(new UpdateLocalizerCommand(drivetrain));

            double multiplier = 1.0;

            if (driver_back.getAsBoolean()) {s.schedule(new ToggleHeadingLockCommand(drivetrain));}
            if (driver_start.getAsBoolean()) {s.schedule(new ToggleFieldCentricCommand(drivetrain));}
            if (driver_left_bumper.getAsBoolean() || driver_right_bumper.getAsBoolean()) {multiplier = 0.5;}

            s.schedule(new TeleOpDriveCommand(drivetrain,
                    new Vec2d(driver.getLeftX(), -driver.getLeftY()),
                    -driver.getRightX(), multiplier));

            bot.intakeToTransferCheck();

            if (driver_left.getAsBoolean()) {s.schedule(new ToIntakeStateCommand(bot));}
            if (driver_up.getAsBoolean()) {s.schedule(new ToTransferStateCommand(bot));}
            if (driver_right.getAsBoolean()) {s.schedule(new ToDepositStateCommand(bot));}
            if (driver_down.getAsBoolean()) {s.schedule(new ToEndgameStateCommand(bot));}

            switch(bot.getBotState()) {
                case INTAKE:
                    if (driver_b.getAsBoolean()) {s.schedule(new ToggleIntakeV4BCommand(intake));}
                    if (driver_x.getAsBoolean()) {s.schedule(new ToggleIntakeSpinnerCommand(intake));}
                    break;
                case TRANSFER:
                    if (driver_a.getAsBoolean()) {s.schedule(new RotateHeadingLockCommand(drivetrain));}
                    break;
                case DEPOSIT:
                    if (driver_b.getAsBoolean()) {}
                    if (driver_y.getAsBoolean()) {}
                    if (driver_x.getAsBoolean()) {}
                    break;
                case ENDGAME:
                    if (driver_b.getAsBoolean()) {}
                    if (driver_y.getAsBoolean()) {}
                    if (driver_x.getAsBoolean()) {}
                    if (driver_a.getAsBoolean()) {}
                    break;
                default:
                    break;
            }

            telemetry.update();
            s.run();
        }
    }
}