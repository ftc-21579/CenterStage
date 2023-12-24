package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import static org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.DriveConstants.MAX_ANG_ACCEL;
import static org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.DriveConstants.TRACK_WIDTH;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleLeftPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ActivateIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIntakePositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

@Config
public class Blue {

    // region STARTING POSITIONS
    public static double leftX = 12, leftY = 64;
    public static double rightX = -36, rightY = 64;
    //endregion

    private Bot bot;
    public Blue(Bot bot) {
        this.bot = bot;
    }

    // region LEFT SIDE TRAJECTORIES
    public TrajectorySequence leftLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .addDisplacementMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).schedule();
            })
            .setReversed(true)
            .lineTo(new Vector2d(12, 40))
            .addDisplacementMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).schedule();
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .setTangent(0)
            .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence leftCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .addTemporalMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineTo(new Vector2d(12, 40))
            .addTemporalMarker(() -> {
                new DepositToggleLeftPixelCommand(bot.deposit).execute();
            })
            .setReversed(false)
            .setTangent(90)
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .splineToSplineHeading(new Pose2d(50, 38, Math.toRadians(180)), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(1.0)
            .setReversed(false)
            .splineTo(new Vector2d(12, 12), Math.toRadians(180))
            .addTemporalMarker(() -> {
                new IntakeIntakePositionCommand(bot.intake).schedule();
                new ActivateIntakeSpinnerCommand(bot.intake).schedule();
            })
            .splineTo(new Vector2d(-48, 12), Math.toRadians(180))
            .waitSeconds(1)
            .setReversed(true)
            .addTemporalMarker(() -> {
                new IntakeTransferPositionCommand(bot.intake).schedule();
                new GrabPixelsCommand(bot.deposit).schedule();
            })
            .splineTo(new Vector2d(12, 12), Math.toRadians(0))
            .splineTo(new Vector2d(48, 32), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DisableIntakeSpinnerCommand(bot.intake).schedule();
            })
            .build();

    public TrajectorySequence leftRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .addDisplacementMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).schedule();
            })
            .setReversed(true)
            .lineTo(new Vector2d(12, 32))
            .addDisplacementMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).schedule();
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .setTangent(0)
            .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
            .waitSeconds(0.5)
            .build();
    // endregion

    // region RIGHT SIDE TRAJECTORIES
    public TrajectorySequence rightLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .addDisplacementMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).schedule();
            })
            .setReversed(true)
            .lineTo(new Vector2d(12, 32))
            .addDisplacementMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).schedule();
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .setTangent(0)
            .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence rightCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .addDisplacementMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).schedule();
            })
            .setReversed(true)
            .lineTo(new Vector2d(12, 32))
            .addDisplacementMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).schedule();
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .setTangent(0)
            .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence rightRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .addDisplacementMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).schedule();
            })
            .setReversed(true)
            .lineTo(new Vector2d(12, 32))
            .addDisplacementMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .setReversed(false)
            .setTangent(0)
            .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
            .waitSeconds(0.5)
            .build();
    // endregion
}