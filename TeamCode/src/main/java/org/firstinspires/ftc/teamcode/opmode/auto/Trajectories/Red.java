package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDropCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Red {

    // region STARTING POSITIONS
    public static double leftX = 12, leftY = 64;
    public static double rightX = -36, rightY = 64;
    //endregion

    private SampleMecanumDrive drive;
    private Bot bot;
    public Red(SampleMecanumDrive drive, Bot bot) {
        this.drive = drive;
        this.bot = bot;
    }

    // region LEFT SIDE TRAJECTORIES
    public TrajectorySequence leftLeft = drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .addDisplacementMarker(() -> {
                new ReleasePixelsCommand(bot.deposit).schedule();
            })
            .build();

    public TrajectorySequence leftCenter = drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .build();

    public TrajectorySequence leftRight = drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
            .build();

    // endregion

    // region RIGHT SIDE TRAJECTORIES
    public TrajectorySequence rightLeft = drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .build();

    public TrajectorySequence rightCenter = drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .build();

    public TrajectorySequence rightRight = drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
            .build();

    // endregion
}
