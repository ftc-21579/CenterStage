package org.firstinspires.ftc.teamcode.common.drive.localization;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.hardware.Encoder;

import java.util.Arrays;
import java.util.List;

public class TwoWheelTrackingLocalizer extends TwoTrackingWheelLocalizer implements Localizer {
    public static double TICKS_PER_REV = 8192;
    public static double WHEEL_RADIUS = 0.6889764; // in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    // TODO: DO THIS https://learnroadrunner.com/dead-wheels.html#two-wheel-odometry FOR THE WHEELS
    public static double PARALLEL_X = 4.723; // X is the up and down direction
    public static double PARALLEL_Y = 4.875; // Y is the strafe direction

    public static double PERPENDICULAR_X = 2.684;
    public static double PERPENDICULAR_Y = -2.836;

    public static double X_MULTIPLIER = 1; // Multiplier in the X direction
    public static double Y_MULTIPLIER = 1; // Multiplier in the Y direction

    // Parallel/Perpendicular to the forward axis
    // Parallel wheel is parallel to the forward axis
    // Perpendicular is perpendicular to the forward axis
    private Encoder parallelEncoder, perpendicularEncoder;

    //private SampleMecanumDrive drive;
    private Bot bot;

    public TwoWheelTrackingLocalizer(HardwareMap hardwareMap, Bot bot) {
        super(Arrays.asList(
                new com.acmerobotics.roadrunner.geometry.Pose2d(PARALLEL_X, PARALLEL_Y, 0),
                new com.acmerobotics.roadrunner.geometry.Pose2d(PERPENDICULAR_X, PERPENDICULAR_Y, Math.toRadians(90))
        ));

        this.bot = bot;

        parallelEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "frontLeft"));
        perpendicularEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "backRight"));

        // TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
        parallelEncoder.setDirection(Encoder.Direction.REVERSE);
        perpendicularEncoder.setDirection(Encoder.Direction.REVERSE);

    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    @Override
    public double getHeading() {
        return bot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    @Override
    public Double getHeadingVelocity() {
        return (double) bot.getImu().getRobotAngularVelocity(AngleUnit.RADIANS).zRotationRate;
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(parallelEncoder.getCurrentPosition()) * X_MULTIPLIER,
                encoderTicksToInches(perpendicularEncoder.getCurrentPosition()) * Y_MULTIPLIER
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        //  If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method

        return Arrays.asList(
                encoderTicksToInches(parallelEncoder.getCorrectedVelocity()) * X_MULTIPLIER,
                encoderTicksToInches(perpendicularEncoder.getCorrectedVelocity()) * Y_MULTIPLIER
        );
    }

    @Override
    public void periodic() {
        super.update();
    }

    @Override
    public Pose2d getPos() {
        com.acmerobotics.roadrunner.geometry.Pose2d pose = super.getPoseEstimate();
        return new Pose2d(pose.getX(), pose.getY(), pose.getHeading());
    }

    @Override
    public void setPos(Pose2d pose) {
        super.setPoseEstimate(new com.acmerobotics.roadrunner.geometry.Pose2d(pose.x, pose.y, pose.heading));
    }
}