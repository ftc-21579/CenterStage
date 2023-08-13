package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.mineinjava.quail.odometry.pathFollower;
import com.mineinjava.quail.odometry.swerveOdometry;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.Vec2d;
import com.mineinjava.quail.odometry.path;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.common.drive.SwerveModule;
import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config
@Autonomous(name="TestAuto")
public class testAuto extends LinearOpMode {

    public static final double steeringGearRatio = 4;
    public static final double driveGearRatio = 1;
    public static double movementMultiplier = 2;
    private DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    private AbsoluteAnalogEncoder leftAbsoluteEncoder, rightAbsoluteEncoder;

    public static double leftkp = 6, leftki = 0, leftkd = 0;
    public static double rightkp = 6, rightki = 0, rightkd = 0;

    private MiniPID leftPID = new MiniPID(leftkp, leftki, leftkd);
    private MiniPID rightPID = new MiniPID(rightkp, rightki, rightkd);

    private SwerveModule left, right;

    private final ArrayList<SwerveModule> modules = new ArrayList<>();

    double[] pointone = {0.0, 0.0};
    double[] pointtwo = {5.0, 5.0};
    private path testPath = new path(new ArrayList<>(Arrays.asList(pointone, pointtwo)));

    private swerveOdometry odo;
    private pathFollower pathFollower;

    IMU imu;

    public void runOpMode() {

        // Initialize the motors
        leftUpperMotor = hardwareMap.get(DcMotor.class, "leftUpperMotor");
        leftLowerMotor = hardwareMap.get(DcMotor.class, "leftLowerMotor");
        rightLowerMotor = hardwareMap.get(DcMotor.class, "rightLowerMotor");
        rightUpperMotor = hardwareMap.get(DcMotor.class, "rightUpperMotor");

        // Initialize the absolute encoders
        leftAbsoluteEncoder = new AbsoluteAnalogEncoder(hardwareMap.get(AnalogInput.class, "leftEncoder"));
        rightAbsoluteEncoder = new AbsoluteAnalogEncoder(hardwareMap.get(AnalogInput.class, "rightEncoder"));

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

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                        )
                )
        );

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Initialize the swerve modules
        left = new SwerveModule(new Vec2d(-0.454, 0), steeringGearRatio, driveGearRatio, leftPID, leftUpperMotor, leftLowerMotor, leftAbsoluteEncoder, telemetry, "Left");
        right = new SwerveModule(new Vec2d(0.454, 0), steeringGearRatio, driveGearRatio, rightPID, rightUpperMotor, rightLowerMotor, rightAbsoluteEncoder, telemetry, "Right");

        // Add the modules to the list of modules
        modules.add(left);
        modules.add(right);

        // Initialize the swerve drive class
        swerveDrive<SwerveModule> drive = new swerveDrive<>(modules);
        SwerveModule[] mods = {left, right};
        odo = new swerveOdometry(drive);

        waitForStart();

        leftPID.reset();
        rightPID.reset();

        while (opModeIsActive()) {
            
        }
    }
}
