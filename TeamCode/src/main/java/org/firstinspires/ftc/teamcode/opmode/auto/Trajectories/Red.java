package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositRightV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleLeftPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Red {

    // region STARTING POSITIONS
    public static double leftX = 12, leftY = -64;
    public static double rightX = -36, rightY = -64;
    public static double backdropX = 49;
    //endregion

    private Bot bot;
    public Red(Bot bot) {
        this.bot = bot;
    }

    // region LEFT SIDE TRAJECTORIES
    public TrajectorySequence leftLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(-36, -32, Math.toRadians(0)))
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(-36, -12))
            .turn(Math.toRadians(180))
            .addTemporalMarker(() -> {
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineTo(new Vector2d(24, -12))
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, -30), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -30))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new DepositToTransferPositionCommand(bot).execute();
            })
            .build();

    public TrajectorySequence leftCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(-36, -12, Math.toRadians(90)))
            .addTemporalMarker(() -> {
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .turn(Math.toRadians(90))
            .lineTo(new Vector2d(24, -12))
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, -36), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -36))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new DepositToTransferPositionCommand(bot).execute();
            })
            .build();

    public TrajectorySequence leftRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(-36, -32, Math.toRadians(180)))
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(-36, -12))
            .addTemporalMarker(() -> {
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineTo(new Vector2d(24, -12))
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, -42), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -42))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new DepositToTransferPositionCommand(bot).execute();
            })
            .build();

    // endregion

    // region RIGHT SIDE TRAJECTORIES
    public TrajectorySequence rightLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(10, -46, Math.toRadians(315)))
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineToSplineHeading(new Pose2d(backdropX, -30, Math.toRadians(180)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -30))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new DepositToTransferPositionCommand(bot).execute();
            })
            .build();

    public TrajectorySequence rightCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(26, -36, Math.toRadians(315)))
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineToSplineHeading(new Pose2d(backdropX, -36, Math.toRadians(180)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -36))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new DepositToTransferPositionCommand(bot).execute();
            })
            .build();

    public TrajectorySequence rightRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(30, -48, Math.toRadians(315)))
            .addTemporalMarker(() -> {
                new DepositToggleRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositToBottomPositionCommand(bot.deposit).execute();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineToSplineHeading(new Pose2d(backdropX, -42, Math.toRadians(180)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -42))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new DepositToTransferPositionCommand(bot).execute();
            })
            .build();

    // endregion
}
