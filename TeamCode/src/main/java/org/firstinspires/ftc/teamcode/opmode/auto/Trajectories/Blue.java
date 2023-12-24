package org.firstinspires.ftc.teamcode.opmode.auto.Trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Blue {

    // region STARTING POSITIONS
    public static double leftX = -36, leftY = -64;
    public static double rightX = 12, rightY = -64;
    //endregion

    private SampleMecanumDrive drive;
    private Bot bot;
    public Blue(SampleMecanumDrive drive, Bot bot) {
        this.drive = drive;
        this.bot = bot;
    }

    // region LEFT SIDE TRAJECTORIES
    public TrajectorySequence leftLeft = drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .build();

    public TrajectorySequence leftCenter = drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .build();

    public TrajectorySequence leftRight = drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(270)))
            .build();
    // endregion

    // region RIGHT SIDE TRAJECTORIES
    public TrajectorySequence rightLeft = drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .build();

    public TrajectorySequence rightCenter = drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .build();

    public TrajectorySequence rightRight = drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(270)))
            .build();
    // endregion
}
