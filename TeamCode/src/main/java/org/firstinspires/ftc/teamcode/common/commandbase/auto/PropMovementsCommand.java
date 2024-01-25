package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.Alliance;
import org.firstinspires.ftc.teamcode.common.centerstage.PropDetector;
import org.firstinspires.ftc.teamcode.common.centerstage.Side;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.opmode.auto.Trajectories.Blue;
import org.firstinspires.ftc.teamcode.opmode.auto.Trajectories.Red;

public class PropMovementsCommand extends CommandBase {
    private final SampleMecanumDrive drive;
    private final PropDetector.PropPosition propPosition;
    private final Side side;
    private final Alliance alliance;
    private Bot bot;

    public PropMovementsCommand(Bot bot, SampleMecanumDrive drive, PropDetector.PropPosition propPosition, Alliance alliance, Side side) {
        this.bot = bot;
        this.drive = drive;
        this.propPosition = propPosition;
        this.alliance = alliance;
        this.side = side;
    }

    @Override
    public void initialize() {
        TrajectorySequence sequence = null;

        switch (alliance) {
            /*
                RED ALLIANCE
             */
            case RED:
                Red r = new Red(bot);
                switch (side) {
                    // LEFT START SIDE
                    case LEFT:
                        switch (propPosition) {
                            case LEFT:
                                sequence = r.leftLeft;
                                break;
                            case CENTER:
                                sequence = r.leftCenter;
                                break;
                            case RIGHT:
                                sequence = r.leftRight;
                                break;
                        }
                        break;
                    // RIGHT START SIDE
                    case RIGHT:
                        switch (propPosition) {
                            case LEFT:
                                sequence = r.rightLeft;
                                break;
                            case CENTER:
                                sequence = r.rightCenter;
                                break;
                            case RIGHT:
                                sequence = r.rightRight;
                                break;
                        }
                        break;
                }
                break;
            /*
                BLUE ALLIANCE
             */
            case BLUE:
                Blue b = new Blue(bot);
                switch (side) {
                    // LEFT START SIDE
                    case LEFT:
                        switch (propPosition) {
                            case LEFT:
                                sequence = b.leftLeft;
                                break;
                            case CENTER:
                                sequence = b.leftCenter;
                                break;
                            case RIGHT:
                                sequence = b.leftRight;
                                break;
                        }
                        break;
                    // RIGHT START SIDE
                    case RIGHT:
                        switch (propPosition) {
                            case LEFT:
                                sequence = b.rightLeft;
                                break;
                            case CENTER:
                                sequence = b.rightCenter;
                                break;
                            case RIGHT:
                                sequence = b.rightRight;
                                break;
                        }
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
