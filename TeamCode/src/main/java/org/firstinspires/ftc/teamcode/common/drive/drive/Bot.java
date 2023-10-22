package org.firstinspires.ftc.teamcode.common.drive.drive;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.BasicCommand;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;
import com.amarcolini.joos.command.RepeatCommand;
import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.mineinjava.quail.odometry.path;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.IntakeV4B;
import org.firstinspires.ftc.teamcode.common.drive.localization.Localizer;
import org.firstinspires.ftc.teamcode.common.drive.localization.TwoDeadwheelLocalizer;

import java.util.ArrayList;

@Config
public class Bot extends Robot {
    /*
        Variables for the bot ABSTRACT WHEN POSSIBLE
     */
    private BotState botState = BotState.TRANSFER;
    private IMU imu;
    public final SuperTelemetry telem;
    public DcMotor parallelPod, perpendicularPod;
    private Localizer localizer;
    private ArrayList<PixelColor> heldPixels = new ArrayList<>();

    /*
        Subsystems
     */
    public Drivetrain drivetrain;
    public Intake intake;
    public IntakeV4B intakeV4B;

    /*
        Constructor for the bot (initialize hardware)
     */
    public Bot(SuperTelemetry telem) {
        this.telem = telem;

        imu = hMap.get(IMU.class, "imu");
        imu.initialize(
            new IMU.Parameters(
                new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                    RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                )
            )
        );

        /* Subsystems */
        drivetrain = new Drivetrain(this);
        intake = new Intake(this);
        intakeV4B = new IntakeV4B(this);

        /* Localizer */
        parallelPod = hMap.get(DcMotor.class, "rightLowerMotor");
        perpendicularPod = hMap.get(DcMotor.class, "leftLowerMotor");
        parallelPod.setDirection(DcMotorSimple.Direction.REVERSE);
        perpendicularPod.setDirection(DcMotorSimple.Direction.REVERSE);

        localizer = new TwoDeadwheelLocalizer(this);
        localizer.setPos(new Pose2d(0, 0, 0));


    }

    /*
        Initialize the bot (schedule commands that need to be run in every OpMode)
     */
    @Override
    public void init() {
        schedule(drivetrain.init());
        schedule(intake.init());
        schedule(intakeV4B.init());

        schedule(new RepeatCommand(drivetrain.updateLocalizer(), -1));
        schedule(new RepeatCommand(new BasicCommand(() -> {
            heldPixels = intake.getPixelColors();
            intakeToTransferCheck();
        }), -1));

        if (isInTeleOp) {
            schedule(new RepeatCommand(drivetrain.teleopDrive(), -1));

            map(gamepad().p1.back::justActivated, drivetrain.toggleHeadingLock());
            map(gamepad().p1.back::justActivated, drivetrain.toggleHeadingLock());
        }
    }

    public Command intakeState() {
        return new InstantCommand(() -> {
            botState = BotState.INTAKE;
            telem.addData("Bot State", botState);

            if (botState != BotState.TRANSFER) {
                schedule(transferState());
            }

            schedule(intake.activate());
            schedule(intakeV4B.intakePosition());

            map(gamepad().p1.b::justActivated, intakeV4B.toggleState());
            map(gamepad().p1.x::justActivated, intake.toggleState());
        });
    }

    public Command transferState() {
        return new InstantCommand(() -> {
            botState = BotState.TRANSFER;
            telem.addData("Bot State", botState);

            schedule(intake.disable());
            schedule(intakeV4B.transferPosition());

            unmap(gamepad().p1.b::justActivated);
            unmap(gamepad().p1.x::justActivated);
            unmap(gamepad().p1.y::justActivated);
            unmap(gamepad().p1.a::justActivated);
        });
    }

    public Command depositState() {
        return new InstantCommand(() -> {
            botState = BotState.DEPOSIT;
            telem.addData("Bot State", botState);

            if (botState != BotState.TRANSFER) {
                schedule(transferState());
            }
        });
    }

    public Command endgameState() {
        return new InstantCommand(() -> {
            botState = BotState.ENDGAME;
            telem.addData("Bot State", botState);

            if (botState != BotState.TRANSFER) {
                schedule(transferState());
            }
        });
    }

    public Command setPath(path p) {
        return new InstantCommand(() -> {
            drivetrain.setPath(p);
        });
    }

    public Command followPath(int repeatCount) {
        return new RepeatCommand(drivetrain.followPath(), repeatCount);
    }

    public BotState getBotState() {
        return botState;
    }

    public Localizer getLocalizer() {
        return localizer;
    }

    public IMU getImu() {
        return imu;
    }

    private void intakeToTransferCheck() {
        if (botState == BotState.INTAKE) {
            if (heldPixels.get(0) != PixelColor.NONE && heldPixels.get(1) != PixelColor.NONE) {
                transferState().execute();
            }
        }
    }
}