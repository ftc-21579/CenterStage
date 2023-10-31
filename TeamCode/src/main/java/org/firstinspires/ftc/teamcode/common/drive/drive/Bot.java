package org.firstinspires.ftc.teamcode.common.drive.drive;

import com.acmerobotics.dashboard.config.Config;

import com.arcrobotics.ftclib.command.Robot;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ActivateIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIntakePositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
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
    public final Telemetry telem;
    public final HardwareMap hMap;
    public DcMotor parallelPod, perpendicularPod;
    private Localizer localizer;
    private ArrayList<PixelColor> heldPixels = new ArrayList<>();

    /*
        Subsystems
     */
    public Drivetrain drivetrain;
    public Intake intake;

    /*
        Constructor for the bot (initialize hardware)
     */
    public Bot(Telemetry telem, HardwareMap hMap) {
        this.telem = telem;
        this.hMap = hMap;

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

        /* Localizer */
        parallelPod = hMap.get(DcMotor.class, "rightLowerMotor");
        perpendicularPod = hMap.get(DcMotor.class, "leftLowerMotor");

        localizer = new TwoDeadwheelLocalizer(this);
        localizer.setPos(new Pose2d(0, 0, 0));


    }


    public void toIntakeState() {
        if (botState != BotState.TRANSFER && botState != BotState.INTAKE) {
            toTransferState();
        }

        botState = BotState.INTAKE;
        telem.addData("Bot State", botState);

        schedule(new ActivateIntakeSpinnerCommand(intake));
        schedule(new IntakeIntakePositionCommand(intake));
    }

    public void toTransferState() {
        botState = BotState.TRANSFER;
        telem.addData("Bot State", botState);

        schedule(new DisableIntakeSpinnerCommand(intake));
        schedule(new IntakeTransferPositionCommand(intake));
    }

    public void toDepositState() {
        botState = BotState.DEPOSIT;
        telem.addData("Bot State", botState);

        if (botState != BotState.TRANSFER) {
            toTransferState();
        }
    }

    public void toEndgameState() {
        botState = BotState.ENDGAME;
        telem.addData("Bot State", botState);

        if (botState != BotState.TRANSFER) {
            toTransferState();
        }
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

    public void intakeToTransferCheck() {
        if (botState == BotState.INTAKE) {
            heldPixels = intake.getPixelColors();

            if (heldPixels.get(0) != PixelColor.NONE && heldPixels.get(1) != PixelColor.NONE) {
                toTransferState();
            }
        }
    }
}