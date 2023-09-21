package org.firstinspires.ftc.teamcode.common.drive.drive;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.RepeatCommand;
import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.amarcolini.joos.hardware.Motor;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.common.drive.localization.Localizer;
import org.firstinspires.ftc.teamcode.common.drive.localization.TwoWheelLocalizer;
import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

import java.util.ArrayList;
import java.util.List;

@Config
public class Bot extends Robot {

    /*
        Variables for the bot ABSTRACT WHEN POSSIBLE
     */
    public static boolean fieldCentric = true;

    public static final double steeringGearRatio = 4;
    public static final double driveGearRatio = 1;
    public DcMotorEx leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    public AbsoluteAnalogEncoder leftAbsoluteEncoder, rightAbsoluteEncoder;

    public static double leftkp = 6, leftki = 0, leftkd = 0;
    public static double rightkp = 6, rightki = 0, rightkd = 0;

    private MiniPID leftPID = new MiniPID(leftkp, leftki, leftkd);
    private MiniPID rightPID = new MiniPID(rightkp, rightki, rightkd);

    private SwerveModule left, right;

    private final List<SwerveModule> modules = new ArrayList<>();
    public swerveDrive<SwerveModule> drive;

    public IMU imu;

    public final SuperTelemetry telem;

    ElapsedTime timer;

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

        // Initialize the motors
        leftUpperMotor = hMap.get(DcMotorEx.class, "leftUpperMotor");
        leftLowerMotor = hMap.get(DcMotorEx.class, "leftLowerMotor");
        rightLowerMotor = hMap.get(DcMotorEx.class, "rightLowerMotor");
        rightUpperMotor = hMap.get(DcMotorEx.class, "rightUpperMotor");

        // Initialize the absolute encoders
        leftAbsoluteEncoder = new AbsoluteAnalogEncoder(hMap.get(AnalogInput.class, "leftEncoder"));
        rightAbsoluteEncoder = new AbsoluteAnalogEncoder(hMap.get(AnalogInput.class, "rightEncoder"));

        leftLowerMotor.setDirection(DcMotor.Direction.REVERSE);
        rightLowerMotor.setDirection(DcMotor.Direction.REVERSE);

        leftLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hMap.get(IMU.class, "imu");
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                        )
                )
        );

        // Initialize the swerve modules
        left = new SwerveModule(new Vec2d(-0.454, 0),
                steeringGearRatio,
                driveGearRatio,
                leftPID,
                leftUpperMotor,
                leftLowerMotor,
                leftAbsoluteEncoder,
                telem,
                "Left"
        );

        right = new SwerveModule(new Vec2d(0.454, 0),
                steeringGearRatio,
                driveGearRatio,
                rightPID,
                rightUpperMotor,
                rightLowerMotor,
                rightAbsoluteEncoder,
                telem,
                "Right"
        );

        // Add the modules to the list of modules
        modules.add(left);
        modules.add(right);

        // Initialize the swerve drive class
        drive = new swerveDrive<>(modules);

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

        schedule(new RepeatCommand(drivetrain.updateLocalizer(), -1));

        if (isInTeleOp) {
            schedule(new RepeatCommand(drivetrain.teleopDrive(), -1));
        }
    }
}