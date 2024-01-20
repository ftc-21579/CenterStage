package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.centerstage.Side;
import org.firstinspires.ftc.teamcode.common.centerstage.Time;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIntakePositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;

public class AutonDelayCommand extends CommandBase {
    private Bot bot;
    private final SampleMecanumDrive drive;
    private final Time time;

    public static double backdropX = 49;

    public AutonDelayCommand(Bot bot, SampleMecanumDrive drive, Time time) {
        this.bot = bot;
        this.drive = drive;
        this.time = time;
    }

    @Override
    public void initialize() {
        TrajectorySequence sequence = null;

        switch(time) {
            case ZERO:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(0.0)
                        .build();

                break;
            case ONE:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(1.0)
                        .build();
                break;
            case TWO:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(2.0)
                        .build();
                break;
            case THREE:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(3.0)
                        .build();
                break;
            case FOUR:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(4.0)
                        .build();
                break;
            case FIVE:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(5.0)
                        .build();
                break;
            case SIX:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(6.0)
                        .build();
                break;
            case SEVEN:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(7.0)
                        .build();
                break;
            case EIGHT:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(8.0)
                        .build();
                break;
            case NINE:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(9.0)
                        .build();
                break;
            case TEN:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(10.0)
                        .build();
                break;
            case ELEVEN:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(11.0)
                        .build();
                break;
            case TWELVE:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(12.0)
                        .build();
                break;
            case THIRTEEN:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(13.0)
                        .build();
                break;
            case FOURTEEN:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(14.0)
                        .build();
                break;
            case FIFTEEN:
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .waitSeconds(15.0)
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
