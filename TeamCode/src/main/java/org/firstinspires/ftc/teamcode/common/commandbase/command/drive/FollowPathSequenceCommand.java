package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.mineinjava.quail.RobotMovement;
import com.mineinjava.quail.util.geometry.Vec2d;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;

public class FollowPathSequenceCommand extends CommandBase {

    private MecanumDrivetrain drivetrain;
    Boolean ready = false;

    public FollowPathSequenceCommand(MecanumDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public void execute() {
        RobotMovement nextDriveMovement = drivetrain.pathSequenceFollower.followPathSequence();
        new TeleOpDriveCommand(drivetrain, new Vec2d(nextDriveMovement.translation.x, -nextDriveMovement.translation.y),
                -nextDriveMovement.rotation,
                1);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
