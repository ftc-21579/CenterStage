package org.firstinspires.ftc.teamcode.common.drive.drive;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.RepeatCommand;
import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.amarcolini.joos.hardware.Motor;
import com.mineinjava.quail.swerveDrive;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.common.drive.localization.Localizer;
import org.firstinspires.ftc.teamcode.common.drive.localization.TwoWheelLocalizer;

@Config
public class Bot extends Robot {
    /*
        Variables for the bot ABSTRACT WHEN POSSIBLE
     */
    public static boolean fieldCentric = true;

    public swerveDrive<SwerveModule> drive;

    public IMU imu;

    public final SuperTelemetry telem;


    public Motor.Encoder parallelPod, perpendicularPod;
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

        parallelPod = hMap.get(Motor.Encoder.class, "parallelPod");
        perpendicularPod = hMap.get(Motor.Encoder.class, "perpindicularPod");
        localizer = new TwoWheelLocalizer(this);

        /* Subsystems */
        drivetrain = new Drivetrain(this);
    }

    /*
        Initialize the bot (schedule commands that need to be run in every opmode)
     */
    @Override
    public void init() {
        schedule(drivetrain.init());
        schedule(new RepeatCommand(drivetrain.updateLocalizer(), -1));

        if (isInTeleOp) {
            schedule(new RepeatCommand(drivetrain.teleopDrive(), -1));
        }
    }
}