package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.centerstage.Side;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIntakePositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

public class AutonParkCommand extends CommandBase {
    private final SampleMecanumDrive drive;
    private final Side side;
    private final Alliance alliance;

    public static double backdropX = 49;

    private Bot bot;

    public AutonParkCommand(Bot bot, SampleMecanumDrive drive, Alliance alliance, Side side) {
        this.bot = bot;
        this.drive = drive;
        this.alliance = alliance;
        this.side = side;
    }

    @Override
    public void initialize() {
        TrajectorySequence sequence = null;

        switch(alliance) {
            case RED:
                switch(side) {
                    case LEFT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineTo(new Vector2d(backdropX - 6, -12))
                                .build();
                        break;
                    case RIGHT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .lineTo(new Vector2d(backdropX - 6, -60))
                                .build();
                        break;
                }
                break;
            case BLUE:
                switch(side) {
                    case LEFT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .forward(6)
                                .lineTo(new Vector2d(backdropX - 6, 60))
                                .build();
                        break;
                    case RIGHT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .forward(6)
                                .lineTo(new Vector2d(backdropX - 6, 12))
                                .build();
                        break;
                }
                break;
        }

        drive.followTrajectorySequenceAsync(sequence);
    }
    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }
}
