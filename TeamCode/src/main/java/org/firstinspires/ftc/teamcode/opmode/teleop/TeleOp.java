package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.UpdateLocalizerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeV4BCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.function.BooleanSupplier;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "FtclibTestOpMode")
public class TeleOp extends LinearOpMode {

    Bot bot;
    Intake intake;
    Drivetrain drivetrain;

    GamepadEx driver;

    @Override
    public void runOpMode() {

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
            s.schedule(new TeleOpDriveCommand(drivetrain,
                    new Vec2d(driver.getLeftX(), driver.getLeftY()),
                    driver.getRightX()));

            bot.intakeToTransferCheck();

            if (driver_left.getAsBoolean()) {bot.toIntakeState();}
            if (driver_up.getAsBoolean()) {bot.toTransferState();}
            if (driver_right.getAsBoolean()) {bot.toDepositState();}
            if (driver_down.getAsBoolean()) {bot.toEndgameState();}

            switch(bot.getBotState()) {
                case INTAKE:
                    if (driver_b.getAsBoolean()) {s.schedule(new ToggleIntakeV4BCommand(intake));}
                    if (driver_x.getAsBoolean()) {s.schedule(new ToggleIntakeSpinnerCommand(intake));}
                    break;
                case TRANSFER:
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
            }

            telemetry.update();
            s.run();
        }
    }
}