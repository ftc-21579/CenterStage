package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Red {

    // region STARTING POSITIONS
    public static double leftX = 12, leftY = -64;
    public static double rightX = -36, rightY = -64;
    //endregion

    private Bot bot;
    public Red(Bot bot) {
        this.bot = bot;
    }

    // region LEFT SIDE TRAJECTORIES
    public TrajectorySequence leftLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
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

    public TrajectorySequence leftCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
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

    public TrajectorySequence leftRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
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
    public TrajectorySequence rightLeft = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
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

    public TrajectorySequence rightCenter = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
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

    public TrajectorySequence rightRight = SampleMecanumDrive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
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
}
