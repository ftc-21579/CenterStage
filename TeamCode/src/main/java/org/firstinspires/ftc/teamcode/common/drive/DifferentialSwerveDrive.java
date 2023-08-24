package org.firstinspires.ftc.teamcode.common.drive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amarcolini.joos.control.PIDCoefficients;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.amarcolini.joos.drive.DriveSignal;
import com.amarcolini.joos.followers.HolonomicPIDVAFollower;
import com.amarcolini.joos.followers.TrajectoryFollower;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.Imu;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.MotorGroup;
import com.amarcolini.joos.hardware.drive.DriveComponent;
import com.amarcolini.joos.localization.Localizer;
import com.amarcolini.joos.trajectory.constraints.TrajectoryConstraints;
import com.mineinjava.quail.robotMovement;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.Vec2d;

import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

import java.util.ArrayList;
import java.util.List;

public class DifferentialSwerveDrive extends DriveComponent {
    public MotorGroup leftMotorGroup, rightMotorGroup;
    public AbsoluteAnalogEncoder leftEncoder, rightEncoder;
    public double steeringGearRatio, driveGearRatio;
    public Boolean isFieldCentric;
    public Imu imu;
    private MiniPID moduleOrientationPID, translationPID, headingPID;
    public PIDCoefficients moduleOrientationCoefficients, translationCoefficients, headingCoefficients;
    private SwerveModule left, right;
    public final List<SwerveModule> modules = new ArrayList<>();
    public swerveDrive<SwerveModule> drive;
    private final SuperTelemetry telem;

    public DifferentialSwerveDrive(
            MotorGroup leftMotorGroup,
            MotorGroup rightMotorGroup,
            AbsoluteAnalogEncoder leftEncoder,
            AbsoluteAnalogEncoder rightEncoder,
            double steeringGearRatio,
            double driveGearRatio,
            Boolean isFieldCentric,
            Imu imu,
            PIDCoefficients moduleOrientationCoefficients,
            PIDCoefficients translationCoefficients,
            PIDCoefficients headingCoefficients,
            SuperTelemetry telem
        ) {

        // Instantiate MiniPIDs for Quail from Joos PIDCoefficients
        moduleOrientationPID = new MiniPID(
                moduleOrientationCoefficients.kP,
                moduleOrientationCoefficients.kI,
                moduleOrientationCoefficients.kD
        );

        translationPID = new MiniPID(
                translationCoefficients.kP,
                translationCoefficients.kI,
                translationCoefficients.kD
        );

        headingPID = new MiniPID(
                headingCoefficients.kP,
                headingCoefficients.kI,
                headingCoefficients.kD
        );

        // Reset the encoders of all drive motors
        leftMotorGroup.resetEncoder();
        rightMotorGroup.resetEncoder();

        // Set the run mode of all drive motors to RUN_USING_ENCODER
        leftMotorGroup.setRunMode(Motor.RunMode.RUN_USING_ENCODER);
        rightMotorGroup.setRunMode(Motor.RunMode.RUN_USING_ENCODER);

        // Create the swerve modules
        left = new SwerveModule(
                new Vec2d(-0.454, 0),
                steeringGearRatio,
                driveGearRatio,
                moduleOrientationPID,
                leftMotorGroup.getMotors()[0],
                leftMotorGroup.getMotors()[1],
                leftEncoder,
                telem,
                "left"
        );

        right = new SwerveModule(
                new Vec2d(0.454, 0),
                steeringGearRatio,
                driveGearRatio,
                moduleOrientationPID,
                rightMotorGroup.getMotors()[0],
                rightMotorGroup.getMotors()[1],
                rightEncoder,
                telem,
                "right"
        );

        // Add the swerve modules to the list of modules
        modules.add(left);
        modules.add(right);

        // Create the swerve drive
        drive = new swerveDrive<>(modules);

        // Assign the parameters to the class variables
        this.leftMotorGroup = leftMotorGroup;
        this.rightMotorGroup = rightMotorGroup;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.steeringGearRatio = steeringGearRatio;
        this.driveGearRatio = driveGearRatio;
        this.isFieldCentric = isFieldCentric;
        this.imu = imu;
        this.moduleOrientationCoefficients = moduleOrientationCoefficients;
        this.translationCoefficients = translationCoefficients;
        this.headingCoefficients = headingCoefficients;
        this.telem = telem;
    }

    public TrajectoryFollower trajectoryFollower = new HolonomicPIDVAFollower(translationCoefficients, translationCoefficients, headingCoefficients);

    @NonNull
    @Override
    public Localizer getLocalizer() {
        return null;
    }

    @Override
    public void setLocalizer(@NonNull Localizer localizer) {

    }

    @Override
    public void setDrivePower(@NonNull Pose2d pose2d) {
        if (isFieldCentric) {
            double botHeading = imu.getHeading().value;
            drive.move(new robotMovement(pose2d.heading.value, new Vec2d(pose2d.y, pose2d.x)),
                    -botHeading);
        } else {
            drive.move(new robotMovement(pose2d.heading.value, new Vec2d(pose2d.y, pose2d.x)),
                    0);
        }
    }

    @Override
    public void setDriveSignal(@NonNull DriveSignal driveSignal) {
        Pose2d pose2d = driveSignal.getVel();
        if (isFieldCentric) {
            double botHeading = imu.getHeading().value;
            drive.move(new robotMovement(pose2d.heading.value, new Vec2d(pose2d.y, pose2d.x)),
                    -botHeading);
        } else {
            drive.move(new robotMovement(pose2d.heading.value, new Vec2d(pose2d.y, pose2d.x)),
                    0);
        }
    }

    @NonNull
    @Override
    public TrajectoryConstraints getConstraints() {
        return null;
    }

    @Nullable
    @Override
    protected Imu getImu() {
        return imu;
    }

    @NonNull
    @Override
    public TrajectoryFollower getTrajectoryFollower() {
        return trajectoryFollower;
    }

    @Override
    public void setTrajectoryFollower(@NonNull TrajectoryFollower trajectoryFollower) {
        this.trajectoryFollower = trajectoryFollower;
    }

    @Override
    public void setRunMode(@NonNull Motor.RunMode runMode) {
        leftMotorGroup.setRunMode(runMode);
        rightMotorGroup.setRunMode(runMode);
    }

    @Override
    public void setZeroPowerBehavior(@NonNull Motor.ZeroPowerBehavior zeroPowerBehavior) {
        leftMotorGroup.setZeroPowerBehavior(zeroPowerBehavior);
        rightMotorGroup.setZeroPowerBehavior(zeroPowerBehavior);
    }
}
