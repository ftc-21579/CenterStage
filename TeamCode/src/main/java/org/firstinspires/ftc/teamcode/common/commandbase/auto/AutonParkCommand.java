package org.firstinspires.ftc.teamcode.common.commandbase.auto;

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
                                .addDisplacementMarker(() -> {
                                    // empty
                                })
                                .build();
                        break;
                    case RIGHT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .addTemporalMarker(() -> {
                                    // empt
                                })
                                .build();
                        break;
                }
                break;
            case BLUE:
                switch(side) {
                    case LEFT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .addTemporalMarker(() -> {
                                    // empty
                                })
                                .build();
                        break;
                    case RIGHT:
                        sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                .addDisplacementMarker(() -> {
                                    // empty
                                })
                                .build();
                        break;
                }
                break;
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
