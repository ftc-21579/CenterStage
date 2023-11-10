package org.firstinspires.ftc.teamcode.common.drive.drive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.Robot;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ActivateIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIntakePositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToTransferStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
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
    public DifferentialSwerveDrivetrain drivetrain;
    public Intake intake;
    public DroneLauncher launcher;

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
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
            )
        );

        /* Localizer */
        parallelPod = hMap.get(DcMotor.class, "rightLowerMotor");
        perpendicularPod = hMap.get(DcMotor.class, "leftLowerMotor");

        localizer = new TwoDeadwheelLocalizer(this);
        localizer.setPos(new Pose2d(0, 0, 0));

        /* Subsystems */
        drivetrain = new DifferentialSwerveDrivetrain(this);
        intake = new Intake(this);
        launcher = new DroneLauncher(this);
    }


    public void toIntakeState() {
        if (botState != BotState.TRANSFER && botState != BotState.INTAKE) {
            schedule(new ToTransferStateCommand(this));
        }

        botState = BotState.INTAKE;
        telem.addData("Bot State", botState);

        schedule(new ActivateIntakeSpinnerCommand(intake));
        schedule(new IntakeIntakePositionCommand(intake));
    }

    public void toTransferState() {
        botState = BotState.TRANSFER;
        telem.addData("Bot State", botState);
    }

    public void toDepositState() {
        if (botState != BotState.TRANSFER && botState != BotState.DEPOSIT) {
            schedule(new ToTransferStateCommand(this));
        }

        botState = BotState.DEPOSIT;
        telem.addData("Bot State", botState);
    }

    public void toEndgameState() {
        if (botState != BotState.TRANSFER && botState != BotState.ENDGAME) {
            schedule(new ToTransferStateCommand(this));
        }

        botState = BotState.ENDGAME;
        telem.addData("Bot State", botState);
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
                schedule(new ToTransferStateCommand(this));
            }
        }
    }
}