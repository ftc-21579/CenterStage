package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.mineinjava.quail.RobotMovement;
import com.mineinjava.quail.localization.Localizer;
import com.mineinjava.quail.pathing.Path;
import com.mineinjava.quail.pathing.PathFollower;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

@Config
public class MecanumDrivetrain extends SubsystemBase {
    private Bot bot;

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private PathFollower pathFollower;
    public static boolean fieldCentric = false, headingLock = false;
    public static double speed = 0.25; // % of max speed
    public static double maxAccel = 1; // % of max speed per second
    public static double precision = 2.0; // in
    public static double slowDownRadius = 9.0; // in
    public static double slowDownKp = 0.025;
    public static double autonMaxTurnSpeed = 0.65; // rad/s
    public static double autonMaxTurnAccel = 0.65; // rad/s^2
    public static double turningKp = 0.001, turningKi = 0.0, turningKd = 0.0;

    Path emptyPath = new Path(new ArrayList<Pose2d>(
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

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        pathFollower = new PathFollower((Localizer) bot.getLocalizer(),
                emptyPath,
                speed,
                autonMaxTurnSpeed,
                autonMaxTurnAccel,
                maxAccel,
                new MiniPID(turningKp, turningKi, turningKd),
                precision,
                slowDownRadius,
                slowDownKp);
    }

    public void teleopDrive(Vec2d leftStick, double rx, double multiplier) {
        double x = leftStick.x * multiplier;
        double y = -leftStick.y * multiplier;

        if (!fieldCentric) {
            y *= 1.1; // counteract imperfect strafe
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            double[] powers = {frontLeftPower, frontRightPower, backLeftPower, backRightPower};
            powers = normalizeWheelSpeeds(powers);

            frontLeft.setPower(powers[0]);
            frontRight.setPower(powers[1]);
            backLeft.setPower(powers[2]);
            backRight.setPower(powers[3]);

            return;
        }

        double botHeading = bot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        //rotX *= 1.1; // counteract imperfect strafe

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        double[] powers = {frontLeftPower, frontRightPower, backLeftPower, backRightPower};
        //powers = normalizeWheelSpeeds(powers);

        frontLeft.setPower(powers[0]);
        frontRight.setPower(powers[1]);
        backLeft.setPower(powers[2]);
        backRight.setPower(powers[3]);
    }

    /**
     * Updates the localizer of the Drivetrain
     */
    public void updateLocalizer() {
        bot.getLocalizer().getPose();

        Pose2d current = bot.getLocalizer().getPose();

        bot.telem.addData("Pose X", new DecimalFormat("#.##").format(current.x) + " inches");
        bot.telem.addData("Pose Y", new DecimalFormat("#.##").format(current.y) + " inches");
        bot.telem.addData("Pose Heading", new DecimalFormat("#.##").format(current.heading) + " radians");
    }

    public void setPath(Path p) {
        pathFollower.setPath(p);
    }

    public void followPath(Path p) {
        //updateLocalizer();
        bot.telem.addData("Path Finished", pathFinished());
        bot.telem.addData("Current Point", pathFollower.path.getCurrentPoint());
        if(pathFollower.lastRobotPose != null) {
            bot.telem.addData("dadadata", pathFollower.lastRobotPose.vectorTo(bot.getLocalizer().getPose()).scale(1/ pathFollower.loopTime));
        }

        if (p != pathFollower.path) {
            pathFollower.setPath(p);
        }

        if (!pathFinished()) {

            updateLocalizer();
            bot.telem.addData("poseses", pathFollower.localizer.getPose());
            RobotMovement nextDriveMovement = pathFollower.calculateNextDriveMovement();
            bot.telem.addData("Next Drive Movement",
                    "X: " + nextDriveMovement.translation.x
                            + " Y: " + nextDriveMovement.translation.y
                            + " Rot: " + nextDriveMovement.rotation);
            teleopDrive(new Vec2d(nextDriveMovement.translation.x, -nextDriveMovement.translation.y),
                    -nextDriveMovement.rotation,
                    1);
            return;
        }

        teleopDrive(new Vec2d(0, 0), 0, 1);
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

    private double[] normalizeWheelSpeeds(double[] speeds) {
        if (largestAbsolute(speeds) > 1) {
            double max = largestAbsolute(speeds);
            for (int i = 0; i < speeds.length; i++){
                speeds[i] /= max;
            }
        }
        return speeds;
    }

    private double largestAbsolute(double[] arr) {
        double largestAbsolute = 0;
        for (double d : arr) {
            double absoluteValue = Math.abs(d);
            if (absoluteValue > largestAbsolute) {
                largestAbsolute = absoluteValue;
            }
        }
        return largestAbsolute;
    }
}
