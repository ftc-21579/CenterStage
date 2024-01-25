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
    private final int time;

    public static double backdropX = 49;

    public AutonDelayCommand(Bot bot, SampleMecanumDrive drive, int time) {
        this.bot = bot;
        this.drive = drive;
        this.time = time;
    }

    @Override
    public void initialize() {
        TrajectorySequence sequence = SampleMecanumDrive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .waitSeconds(time)
                .build();
        drive.followTrajectorySequenceAsync(sequence);

    }


    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }
}
