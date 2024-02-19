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
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleLeftPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleV4BCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.ToggleFieldCentricCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drone.LaunchDroneCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drone.ResetDroneLauncherCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.HeadingServoPowerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeDecrementCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIncrementCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ReverseIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ToggleIntakeV4BCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.CustomLiftPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualExtensionInCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualExtensionOutCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualLiftDownCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ManualLiftUpCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.SuperCustomLiftPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToDepositStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToEndgameStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToIntakeStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToTransferStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.Bot;

import java.util.ArrayList;
import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "OneDriver")
public class OneDriver extends LinearOpMode {

    private static boolean autoTransfer = true;
    private int loopCount = 0, a = 0;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Bot bot = new Bot(telemetry, hardwareMap);
        Intake intake = bot.intake;
        MecanumDrivetrain drivetrain = bot.drivetrain;
        DroneLauncher launcher = bot.launcher;
        Deposit deposit = bot.deposit;
        GamepadEx driver = new GamepadEx(gamepad1);

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
            if (driver.wasJustPressed(GamepadKeys.Button.START))
                {s.schedule(new ToggleFieldCentricCommand(drivetrain));}
            if (driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) || driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER))
                {multiplier = 0.5;}

            s.schedule(new TeleOpDriveCommand(drivetrain,
                    new Vec2d(driver.getLeftX(), -driver.getLeftY()),
                    driver.getRightX(), multiplier));

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
                    if (driver.isDown(GamepadKeys.Button.RIGHT_BUMPER))
                        {s.schedule(new ManualExtensionOutCommand(bot.pto, 1.0));}
                    if (driver.isDown(GamepadKeys.Button.LEFT_BUMPER))
                        {s.schedule(new ManualExtensionInCommand(bot.pto, 1.0));}

                    s.schedule(new HeadingServoPowerCommand(intake, driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)));
                    s.schedule(new HeadingServoPowerCommand(intake, -driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)));
                    break;
                case TRANSFER:

                    break;
                case DEPOSIT:
                    if (driver.isDown(GamepadKeys.Button.A))
                        {s.schedule(new ReleasePixelsCommand(bot.deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.B))
                        {s.schedule(new DepositToggleLeftPixelCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y))
                        {s.schedule(new DepositToggleV4BCommand(deposit));}
                    if (driver.wasJustPressed(GamepadKeys.Button.X))
                        {s.schedule(new DepositToggleRightPixelCommand(deposit));}

                    if (driver.isDown(GamepadKeys.Button.RIGHT_BUMPER))
                        {s.schedule(new ManualLiftUpCommand(bot.pto, 1.0));}
                    if (driver.isDown(GamepadKeys.Button.LEFT_BUMPER))
                        {s.schedule(new ManualLiftDownCommand(bot.pto, 1.0));}

                    break;
                case ENDGAME:
                    if (driver.wasJustPressed(GamepadKeys.Button.B)) {s.schedule(new LaunchDroneCommand(launcher));}
                    if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
                        s.schedule(new CustomLiftPositionCommand(bot.pto, Configs.liftHangHeightPosition));
                        s.schedule(new DepositV4BToIdleCommand(deposit));
                    }
                    //if (driver.isDown(GamepadKeys.Button.X)) {s.schedule(new ManualLiftDownCommand(bot.pto, 8.0));}
                    if (driver.wasJustPressed(GamepadKeys.Button.X)) {s.schedule(new SuperCustomLiftPositionCommand(bot.pto, 370.0, -430.0));}
                    if (driver.wasJustPressed(GamepadKeys.Button.A)) {s.schedule(new ResetDroneLauncherCommand(launcher));}
                    break;
                default:
                    break;
            }


            //s.schedule(new RunLiftPIDCommand(deposit));

            /*
            if (driver.isDown(GamepadKeys.Button.BACK)) {
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

            if (loopCount > 5 && autoTransfer) {
                bot.intakeToTransferCheck();
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
                    case WHITE:
                        gamepad1.setLedColor(255, 255, 255, Gamepad.LED_DURATION_CONTINUOUS);
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
                    case WHITE:
                        gamepad2.setLedColor(255, 255, 255, Gamepad.LED_DURATION_CONTINUOUS);
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