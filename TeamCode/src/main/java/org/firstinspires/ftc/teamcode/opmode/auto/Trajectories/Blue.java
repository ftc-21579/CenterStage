package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositLeftV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositRightV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositRightV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.CustomLiftPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Blue {

    // region STARTING POSITIONS
    public static double leftX = 12, leftY = 64;
    public static double rightX = -32.5, rightY = 64;
    public static double backdropX = 49;
    //endregion

    private Bot bot;
    public Blue(Bot bot) {
        this.bot = bot;
    }

    // region LEFT SIDE TRAJECTORIES
    public TrajectorySequence leftLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(28, 48, Math.toRadians(45)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).execute();
                new DepositRightV4BToIdleCommand(bot.deposit).execute();
                new DepositLeftV4BToDepositCommand(bot.deposit).execute();
            })
            .lineToSplineHeading(new Pose2d(backdropX, 46, Math.toRadians(180)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence leftCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(26, 38, Math.toRadians(45)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineToSplineHeading(new Pose2d(backdropX, 37, Math.toRadians(180)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence leftRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .setReversed(true)
            .lineTo(new Vector2d(leftX, 60))
            .turn(Math.toRadians(-90))
            .lineToSplineHeading(new Pose2d(leftX + 3, 40, Math.toRadians(0)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .waitSeconds(1.0)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineToSplineHeading(new Pose2d(backdropX, 32, Math.toRadians(180)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .build();
    // endregion

    // region RIGHT SIDE TRAJECTORIES
    public TrajectorySequence rightLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .setReversed(true)
            .lineTo(new Vector2d(rightX, 60))
            .turn(Math.toRadians(90))
            .lineToSplineHeading(new Pose2d(rightX - 5, 32, Math.toRadians(180)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositV4BToIdleCommand(bot.deposit).execute();
            })
            .lineTo(new Vector2d(rightX, 14))
            .addTemporalMarker(() -> {
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineTo(new Vector2d(24, 14))
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, 46), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence rightCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(rightX, 12, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .turn(Math.toRadians(-90))
            .lineTo(new Vector2d(24, 12))
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, 38), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence rightRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineTo(new Vector2d(rightX, 60))
            .turn(Math.toRadians(-90))
            .lineToSplineHeading(new Pose2d(rightX + 2, 36, Math.toRadians(0)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(rightX, 14))
            .addTemporalMarker(() -> {
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .turn(Math.toRadians(180))
            .lineTo(new Vector2d(24, 14))
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, 34), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .build();
    // endregion
}
