package org.firstinspires.ftc.teamcode.common.drive.localization;

import androidx.annotation.NonNull;

import com.mineinjava.quail.util.geometry.Pose2d;
import com.mineinjava.quail.localization.TwoWheelLocalizer;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.geometry.Pose;

import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleSupplier;

public class QuailTwoWheelLocalizer extends TwoWheelLocalizer implements Localizer {

    public static double TICKS_PER_REV = 8192;
    public static double WHEEL_RADIUS = 0.689;
    public static double GEAR_RATIO = 1;

    public static double PARALLEL_X = 4.648; // TODO: Change this to the actual value (in)
    public static double PARALLEL_Y = 5.524; // TODO: Change this to the actual value (in)

    public static double PERPENDICULAR_X = 0.145; // TODO: Change this to the actual value (in)
    public static double PERPENDICULAR_Y = 1.711; // TODO: Change this to the actual value (in)

    private final DoubleSupplier horizontalPosition, lateralPosition;
    private final double imuAngle;

    private final Bot bot;

    public QuailTwoWheelLocalizer(Bot bot) {
        super(Arrays.asList(
                new Pose2d(PARALLEL_X, PARALLEL_Y, 0),
                new Pose2d(PERPENDICULAR_X, PERPENDICULAR_Y, Math.toRadians(90))
        ));

        this.horizontalPosition = bot.parallelPod::getCurrentPosition;
        this.lateralPosition = bot.perpendicularPod::getCurrentPosition;
        this.imuAngle = bot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        this.bot = bot;
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    @Override
    public double getHeading() {
        return (bot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
    }

    @Override
    public Double getHeadingVelocity() {
        return 0.0;
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(horizontalPosition.getAsDouble()),
                encoderTicksToInches(lateralPosition.getAsDouble())
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        return Arrays.asList(0.0, 0.0);
    }

    @Override
    public Pose getPos() {
        Pose2d pose = super.getPoseEstimate();
        return new Pose(pose.x, -pose.y, pose.heading);
    }

    @Override
    public void setPos(Pose pose) {
    }

    @Override
    public void periodic() {
        super.update();
    }
}
