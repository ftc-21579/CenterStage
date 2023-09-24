package org.firstinspires.ftc.teamcode.common.drive.drive;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.RepeatCommand;
import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.mineinjava.quail.localization.TwoWheelLocalizer;
import com.mineinjava.quail.swerveDrive;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.common.drive.geometry.Pose;
import org.firstinspires.ftc.teamcode.common.drive.localization.Localizer;
import org.firstinspires.ftc.teamcode.common.drive.localization.TwoDeadwheelLocalizer;

@Config
public class Bot extends Robot {
    /*
        Variables for the bot ABSTRACT WHEN POSSIBLE
     */
    public static boolean fieldCentric = false;

    public swerveDrive<SwerveModule> drive;

    public IMU imu;

    public final SuperTelemetry telem;


    public DcMotor parallelPod, perpendicularPod;
    public Localizer localizer;

    /*
        Subsystems
     */
    private Drivetrain drivetrain;


    /*
        Constructor for the bot (initialize hardware)
     */
    public Bot(SuperTelemetry telem) {
        this.telem = telem;

        imu = hMap.get(IMU.class, "imu");
        imu.initialize(
            new IMU.Parameters(
                new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                )
            )
        );

        /* Subsystems */
        drivetrain = new Drivetrain(this);

        /* Localizer */
        parallelPod = hMap.get(DcMotor.class, "rightLowerMotor");
        perpendicularPod = hMap.get(DcMotor.class, "leftLowerMotor");
        localizer = new TwoDeadwheelLocalizer(this);
        localizer.setPos(new Pose(0, 0, 0));
    }

    /*
        Initialize the bot (schedule commands that need to be run in every opmode)
     */
    @Override
    public void init() {
        schedule(drivetrain.init());
        schedule(new RepeatCommand(drivetrain.updateLocalizer(), -1));

        if (isInTeleOp) {
            //schedule(new RepeatCommand(drivetrain.teleopDrive(), -1));
            schedule(new RepeatCommand(drivetrain.pidTune(), -1));
        }
    }
}