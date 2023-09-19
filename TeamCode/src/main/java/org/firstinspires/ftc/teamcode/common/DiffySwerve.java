package org.firstinspires.ftc.teamcode.common;

import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.amarcolini.joos.geometry.Vector2d;
import com.mineinjava.quail.odometry.swerveOdometry;
import com.mineinjava.quail.robotMovement;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.SwerveModule;
import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

import java.util.ArrayList;
import java.util.List;

public class DiffySwerve extends Robot {

    public static boolean fieldCentric = true;

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

    private final List<SwerveModule> modules = new ArrayList<>();
    public swerveDrive<SwerveModule> drive;

    IMU imu;

    private final SuperTelemetry telem;

    public swerveOdometry odometry;

    public DiffySwerve(SuperTelemetry telem) {
        this.telem = telem;

        // Initialize the motors
        leftUpperMotor = hMap.get(DcMotor.class, "leftUpperMotor");
        leftLowerMotor = hMap.get(DcMotor.class, "leftLowerMotor");
        rightLowerMotor = hMap.get(DcMotor.class, "rightLowerMotor");
        rightUpperMotor = hMap.get(DcMotor.class, "rightUpperMotor");

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
                                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
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

        odometry = new swerveOdometry(drive);
        odometry.updateOdometry(new Vec2d(0, 0));
    }

    @Override
    public void init() {

        if (isInTeleOp) {
            schedule(true, teleopDrive());
        }

        schedule(true, updateSwerveOdo());
    }

    /** The standard drive command for teleop, supports field centric if fieldCentric
     * @return Runnable command for teleop
     */
    public Runnable teleopDrive() {
        return () -> {
            Vector2d leftStick = gamepad().p1.getLeftStick();
            double x = leftStick.x;
            double y = leftStick.y;
            double rot = gamepad().p1.getRightStick().x;

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            if (fieldCentric) {
                drive.move(new robotMovement(rot, new Vec2d(y, x)), -botHeading);
            } else {
                drive.move(new robotMovement(rot, new Vec2d(y, x)), 0);
            }

            telem.addLine("In Drive");
        };
    }

    public Runnable updateSwerveOdo() {
        return () -> {
            double leftSpeed = (leftLowerMotor.getCurrentPosition() +
                                leftUpperMotor.getCurrentPosition()) /
                                driveGearRatio;
            double rightSpeed = (rightLowerMotor.getCurrentPosition() +
                                rightUpperMotor.getCurrentPosition()) /
                                driveGearRatio;

            double leftHeading = Math.toRadians(leftAbsoluteEncoder.getCurrentPosition());
            double rightHeading = Math.toRadians(rightAbsoluteEncoder.getCurrentPosition());

            ArrayList<Vec2d> vecs = new ArrayList<>();
            vecs.add(new Vec2d(leftSpeed, leftHeading));
            vecs.add(new Vec2d(rightSpeed, rightHeading));

            odometry.calculateOdometry(vecs);

            telem.addData("Current Odo X", odometry.x);
            telem.addData("Current Odo Y", odometry.y);
        };
    }
}