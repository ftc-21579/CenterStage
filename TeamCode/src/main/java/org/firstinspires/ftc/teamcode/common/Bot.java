package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;

import com.arcrobotics.ftclib.command.Robot;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToTransferStateCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

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
    private ArrayList<PixelColor> heldPixels = new ArrayList<>();

    /*
        Subsystems
     */
    public MecanumDrivetrain drivetrain;
    public Intake intake;
    public Deposit deposit;
    public DroneLauncher launcher;
    public PTO pto;

    public RevBlinkinLedDriver blinkin;

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
                    RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                )
            )
        );

        blinkin = hMap.get(RevBlinkinLedDriver.class, "blinkin");
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);

        /* Localizer */
        //parallelPod = hMap.get(DcMotorEx.class, "frontLeft");
        //perpendicularPod = hMap.get(Motor.Encoder.class, "backRight");

        /* Subsystems */
        drivetrain = new MecanumDrivetrain(this);
        intake = new Intake(this);
        launcher = new DroneLauncher(this);
        deposit = new Deposit(this);
        pto = new PTO(this);
    }


    public void toIntakeState() {
        botState = BotState.INTAKE;
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    public void toTransferState() {
        botState = BotState.TRANSFER;
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
    }

    public void toDepositState() {
        botState = BotState.DEPOSIT;
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
    }

    public void toEndgameState() {
        botState = BotState.ENDGAME;
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
    }

    public BotState getBotState() {
        return botState;
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