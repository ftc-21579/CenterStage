package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.BasicCommand;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;
import com.amarcolini.joos.geometry.Vector2d;
import com.mineinjava.quail.robotMovement;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.drive.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.common.drive.geometry.Pose;
import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Config
public class Drivetrain {
    Bot bot;

    public static final double steeringGearRatio = 4;
    public static final double driveGearRatio = 1;
    private DcMotorEx leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    private AbsoluteAnalogEncoder leftAbsoluteEncoder, rightAbsoluteEncoder;

    public static double leftkp = 6, leftki = 0, leftkd = 0;
    public static double rightkp = 6, rightki = 0, rightkd = 0;

    private MiniPID leftPID = new MiniPID(leftkp, leftki, leftkd);
    private MiniPID rightPID = new MiniPID(rightkp, rightki, rightkd);
    private SwerveModule left, right;
    private final List<SwerveModule> modules = new ArrayList<>();


    /**
     * Encapsulates the drivetrain subsystem commands
     * @param bot
     */
    public Drivetrain(Bot bot) {
        this.bot = bot;
    }

    /** The standard drive command for teleop, supports field centric if fieldCentric
     * @return Command
     */
    public Command teleopDrive() {
        return new BasicCommand(() -> {
            Vector2d leftStick = bot.gamepad().p1.getLeftStick();
            double x = -leftStick.x;
            double y = -leftStick.y;
            double rot = -bot.gamepad().p1.getRightStick().x;

            double botHeading = bot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            if (bot.fieldCentric) {
                bot.drive.move(new robotMovement(rot, new Vec2d(y, x)), -botHeading);
            } else {
                bot.drive.move(new robotMovement(rot, new Vec2d(y, x)), 0);
            }
        });
    }

    public Command pidTune() {
        return new BasicCommand(() -> {
           if (bot.gamepad().p1.dpad_up.getState()) {
               bot.drive.move(new robotMovement(0, new Vec2d(1, 0)), 0);
           } else if (bot.gamepad().p1.dpad_right.getState()) {
               bot.drive.move(new robotMovement(0, new Vec2d(0, 1)), 0);
           } else if (bot.gamepad().p1.dpad_down.getState()) {
               bot.drive.move(new robotMovement(0, new Vec2d(-1, 0)), 0);
           } else if (bot.gamepad().p1.dpad_left.getState()) {
               bot.drive.move(new robotMovement(0, new Vec2d(0, -1)), 0);
           } else {
                bot.drive.move(new robotMovement(0, new Vec2d(0, 0)), 0);
           }
        });
    }

    /**
     * Updates the localizer of the Drivetrain
     * @return Command
     */
    public Command updateLocalizer() {
        return new BasicCommand(() -> {
            bot.localizer.periodic();

            Pose current = bot.localizer.getPos();

            bot.telem.addData("Pose X", new DecimalFormat("#.##").format(current.x) + " inches");
            bot.telem.addData("Pose Y", new DecimalFormat("#.##").format(current.y) + " inches");
            bot.telem.addData("Pose Heading", new DecimalFormat("#.##").format(current.heading) + " radians");
        });
    }

    public Command init() {
        return new InstantCommand(() -> {
            // Initialize the motors
            leftUpperMotor = bot.hMap.get(DcMotorEx.class, "leftUpperMotor");
            leftLowerMotor = bot.hMap.get(DcMotorEx.class, "leftLowerMotor");
            rightLowerMotor = bot.hMap.get(DcMotorEx.class, "rightLowerMotor");
            rightUpperMotor = bot.hMap.get(DcMotorEx.class, "rightUpperMotor");

            // Initialize the absolute encoders
            leftAbsoluteEncoder = new AbsoluteAnalogEncoder(bot.hMap.get(AnalogInput.class, "leftEncoder"));
            rightAbsoluteEncoder = new AbsoluteAnalogEncoder(bot.hMap.get(AnalogInput.class, "rightEncoder"));

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

            // Initialize the swerve modules
            left = new SwerveModule(new Vec2d(-0.454, 0),
                    steeringGearRatio,
                    driveGearRatio,
                    leftPID,
                    leftUpperMotor,
                    leftLowerMotor,
                    leftAbsoluteEncoder,
                    bot.telem,
                    "Left"
            );

            right = new SwerveModule(new Vec2d(0.454, 0),
                    steeringGearRatio,
                    driveGearRatio,
                    rightPID,
                    rightUpperMotor,
                    rightLowerMotor,
                    rightAbsoluteEncoder,
                    bot.telem,
                    "Right"
            );

            // Add the modules to the list of modules
            modules.add(left);
            modules.add(right);

            // Initialize the swerve drive class
            bot.drive = new swerveDrive<>(modules);
        });
    }
}
