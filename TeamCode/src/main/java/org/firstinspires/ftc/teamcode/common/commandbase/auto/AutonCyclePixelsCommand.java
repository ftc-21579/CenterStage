package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.opmode.auto.Trajectories.Blue;
import org.firstinspires.ftc.teamcode.opmode.auto.Trajectories.Red;

public class AutonCyclePixelsCommand extends CommandBase {
    private Bot bot;
    private SampleMecanumDrive drive;
    private Alliance alliance;
    public AutonCyclePixelsCommand(SampleMecanumDrive drive, Bot bot, Alliance alliance) {
        this.drive = drive;
        this.bot = bot;
        this.alliance = alliance;
    }

    @Override
    public void initialize() {
        TrajectorySequence sequence = null;

        switch(alliance) {
            case RED:
                Red r = new Red(bot);
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .addTemporalMarker(() -> {

                        })
                        .build();
                break;
            case BLUE:
                Blue b = new Blue(bot);
                sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .addTemporalMarker(() -> {

                        })
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
