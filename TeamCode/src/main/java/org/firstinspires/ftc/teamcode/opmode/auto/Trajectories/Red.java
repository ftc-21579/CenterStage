package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositRightV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToggleRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleaseRightPixelCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.CustomLiftPositionCommand;
import org.firstinspires.ftc.teamcode.common.Bot;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ResetPTOCommand;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Red {

    // region STARTING POSITIONS
    public static double leftX = -32.5, leftY = -64;
    public static double rightX = 12, rightY = -64;
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
            .lineTo(new Vector2d(leftX, -60))
            .turn(Math.toRadians(90))
            .lineToSplineHeading(new Pose2d(leftX + 3, -34, Math.toRadians(0)))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleaseRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(leftX, -14))
            .turn(Math.toRadians(180))
            .lineTo(new Vector2d(24, -14))
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).schedule();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, -31), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -31))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence leftCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(leftX, -12, Math.toRadians(91)))
            .addTemporalMarker(() -> {
                new DepositV4BToDropCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleaseRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .turn(Math.toRadians(89))
            .lineTo(new Vector2d(24, -14))
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).schedule();
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .splineToConstantHeading(new Vector2d(backdropX, -35), Math.toRadians(0))
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .lineTo(new Vector2d(backdropX - 6, -36))
            .addTemporalMarker(() -> {
                new GrabPixelsCommand(bot.deposit).execute();
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence leftRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .setReversed(true)
            .lineTo(new Vector2d(leftX, leftY + 4))
            .turn(Math.toRadians(-90))
            .lineToSplineHeading(new Pose2d(leftX - 5, -40, Math.toRadians(180)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleaseRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new DepositV4BToIdleCommand(bot.deposit).execute();
            })
            .lineTo(new Vector2d(leftX - 5, -14))
            .addTemporalMarker(() -> {
                new DepositV4BToDepositCommand(bot.deposit).execute();
            })
            .lineTo(new Vector2d(24, -14))
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).schedule();
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
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).execute();
            })
            .waitSeconds(0.5)
            .build();

    // endregion

    // region RIGHT SIDE TRAJECTORIES
    public TrajectorySequence rightLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .setReversed(true)
            .lineTo(new Vector2d(rightX, -60))
            .turn(Math.toRadians(90))
            .lineToSplineHeading(new Pose2d(rightX + 4, -34, Math.toRadians(0)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new ReleaseRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).schedule();
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
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence rightCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(28, -36, Math.toRadians(315)))
            .addTemporalMarker(() -> {
                new ReleaseRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).schedule();
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
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).execute();
            })
            .waitSeconds(0.5)
            .build();

    public TrajectorySequence rightRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .addTemporalMarker(() -> {
                new DepositRightV4BToDropCommand(bot.deposit).execute();
            })
            .setReversed(true)
            .lineToSplineHeading(new Pose2d(36, -48, Math.toRadians(315)))
            .addTemporalMarker(() -> {
                new ReleaseRightPixelCommand(bot.deposit).execute();
            })
            .waitSeconds(0.5)
            .addTemporalMarker(() -> {
                new CustomLiftPositionCommand(bot.pto, Configs.liftAutonBackdropPosition).schedule();
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
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).execute();
            })
            .waitSeconds(0.5)
            .build();

    // endregion
}
