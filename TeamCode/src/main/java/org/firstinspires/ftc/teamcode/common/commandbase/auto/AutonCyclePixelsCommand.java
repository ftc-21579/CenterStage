package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ActivateIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeCustomHeightCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeDecrementCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.CustomLiftPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ResetPTOCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.state.ToTransferStateCommand;
import org.firstinspires.ftc.teamcode.common.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

public class AutonCyclePixelsCommand extends CommandBase {
    private Bot bot;
    private SampleMecanumDrive drive;
    private Alliance alliance;
    public AutonCyclePixelsCommand(SampleMecanumDrive drive, Bot bot, Alliance alliance) {
        this.drive = drive;
        this.bot = bot;
        this.alliance = alliance;
    }

    public static double backdropX = 47;

    @Override
    public void initialize() {
        TrajectorySequence sequence = null;

        switch(alliance) {
            case RED:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(false)
                        .splineToConstantHeading(new Vector2d(24, -12), Math.toRadians(180))
                        .addTemporalMarker(() -> {
                            new ResetPTOCommand(bot.pto).execute();
                            new DepositV4BToDepositCommand(bot.deposit).execute();
                        })
                        .lineTo(new Vector2d(-48, -13))
                        .addTemporalMarker(() -> {
                            new IntakeCustomHeightCommand(bot, Configs.intakeAboveStackPosition).schedule();
                            new ActivateIntakeSpinnerCommand(bot.intake).schedule();
                        })
                        .waitSeconds(2)
                        .lineTo(new Vector2d(-51.5, -13))
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new IntakeDecrementCommand(bot.intake).schedule();
                        })
                        .waitSeconds(1)
                        .turn(Math.toRadians(-5))
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new IntakeDecrementCommand(bot.intake).schedule();
                            new IntakeDecrementCommand(bot.intake).schedule();
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new ToTransferStateCommand(bot).schedule();
                        })
                        .lineToSplineHeading(new Pose2d(24, -12, Math.toRadians(0)))
                        .addTemporalMarker(() -> {
                            new CustomLiftPositionCommand(bot.pto, Configs.liftBottomPosition).execute();
                            new DepositV4BToDepositCommand(bot.deposit).execute();
                        })
                        .splineToConstantHeading(new Vector2d(backdropX, -38), Math.toRadians(0))
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new ReleasePixelsCommand(bot.deposit).execute();
                        })
                        .waitSeconds(1)
                        .build();
                break;
            case BLUE:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .setReversed(false)
                        .splineToConstantHeading(new Vector2d(24, 12), Math.toRadians(180))
                        .addTemporalMarker(() -> {
                            new ResetPTOCommand(bot.pto).execute();
                            new DepositV4BToDepositCommand(bot.deposit).execute();
                        })
                        .lineTo(new Vector2d(-48, 13))
                        .addTemporalMarker(() -> {
                            new IntakeCustomHeightCommand(bot, Configs.intakeAboveStackPosition).schedule();
                            new ActivateIntakeSpinnerCommand(bot.intake).schedule();
                        })
                        .waitSeconds(2)
                        .lineTo(new Vector2d(-50.5, 12.5))
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new IntakeDecrementCommand(bot.intake).schedule();
                            new IntakeDecrementCommand(bot.intake).schedule();
                        })
                        .waitSeconds(1)
                        .turn(Math.toRadians(7.5))
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new IntakeDecrementCommand(bot.intake).schedule();
                            new IntakeDecrementCommand(bot.intake).schedule();
                            new IntakeDecrementCommand(bot.intake).schedule();
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new ToTransferStateCommand(bot).schedule();
                        })
                        .lineToSplineHeading(new Pose2d(20, 12, Math.toRadians(180)))
                        .addTemporalMarker(() -> {
                            new CustomLiftPositionCommand(bot.pto, Configs.liftBottomPosition).execute();
                            new DepositV4BToDepositCommand(bot.deposit).execute();
                        })
                        .splineToConstantHeading(new Vector2d(backdropX, 38), Math.toRadians(180))
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            new ReleasePixelsCommand(bot.deposit).execute();
                        })
                        .waitSeconds(1)
                        .build();
                break;
        }

        drive.followTrajectorySequenceAsync(sequence);
    }

    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }
}
