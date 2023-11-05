package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.mineinjava.quail.localization.Localizer;
import com.mineinjava.quail.odometry.path;
import com.mineinjava.quail.odometry.pathFollower;
import com.mineinjava.quail.robotMovement;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

@Config
public class MecanumDrivetrain extends SubsystemBase {
    private Bot bot;

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private pathFollower pathFollower;
    public static boolean fieldCentric = false, headingLock = false;
    public static double autonSpeed = 0.5;
    public static double autonMaxTurnSpeed = 0.5;
    public static double autonMaxTurnAccel = 0.5;
    public static double autonPrecision = 1.0;
    public static double turningKp = 1.0, turningKi = 0.0, turningKd = 0.0;

    path emptyPath = new path(new ArrayList<Pose2d>(
            Arrays.asList(
                    new Pose2d(0, 0, 0)
            )
    ));

    public MecanumDrivetrain(Bot bot) {
        this.bot = bot;

        frontLeft = bot.hMap.get(DcMotorEx.class, "frontLeft");
        frontRight = bot.hMap.get(DcMotorEx.class, "frontRight");
        backLeft = bot.hMap.get(DcMotorEx.class, "backLeft");
        backRight = bot.hMap.get(DcMotorEx.class, "backRight");

        pathFollower = new pathFollower((Localizer) bot.getLocalizer(),
                emptyPath,
                0.5,
                0.5,
                0.5,
                0.5,
                new MiniPID(1, 0, 0),
                1.0);
    }

    public void teleopDrive(Vec2d leftStick, double rx, double multiplier) {
        double x = leftStick.x * multiplier;
        double y = -leftStick.y * multiplier;
        rx *= 0.6;

        if (!fieldCentric) {
            y *= 1.1; // counteract imperfect strafe
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            return;
        }

        double botHeading = bot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX *= 1.1; // counteract imperfect strafe

        double denominator = Math.max(Math.abs(rotX) + Math.abs(rotY) + Math.abs(rx), 1);
        double frontLeftPower = (rotX + rotY + rx) / denominator;
        double frontRightPower = (rotX - rotY - rx) / denominator;
        double backLeftPower = (rotX - rotY + rx) / denominator;
        double backRightPower = (rotX + rotY - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    /**
     * Updates the localizer of the Drivetrain
     */
    public void updateLocalizer() {
        bot.getLocalizer().periodic();

        Pose2d current = bot.getLocalizer().getPos();

        bot.telem.addData("Pose X", new DecimalFormat("#.##").format(current.x) + " inches");
        bot.telem.addData("Pose Y", new DecimalFormat("#.##").format(current.y) + " inches");
        bot.telem.addData("Pose Heading", new DecimalFormat("#.##").format(current.heading) + " radians");
    }

    public void setPath(path p) {
        pathFollower.setPath(p);
    }

    public void followPath(path p) {
        bot.telem.addData("Path Finished", pathFinished());
        bot.telem.addData("Next Point", pathFollower.path.getNextPoint());

        if (p != pathFollower.path) {
            pathFollower.setPath(p);
        }

        if (!pathFinished()) {

            robotMovement nextDriveMovement = pathFollower.calculateNextDriveMovement();
            bot.telem.addData("Next Drive Movement",
                    "X: " + nextDriveMovement.translation.x
                            + " Y: " + nextDriveMovement.translation.y
                            + " Rot: " + nextDriveMovement.rotation);
            teleopDrive(nextDriveMovement.translation,
                    nextDriveMovement.rotation,
                    1);
        }
    }

    public boolean pathFinished() {
        return pathFollower.isFinished();
    }

    public void toggleHeadingLock() {
        headingLock = !headingLock;
    }

    public void toggleFieldCentric() {
        fieldCentric = !fieldCentric;
    }
}