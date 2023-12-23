package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.mineinjava.quail.RobotMovement;
import com.mineinjava.quail.util.geometry.Vec2d;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class FollowPathSequenceCommand extends CommandBase {

    private Bot bot;
    Boolean ready = false;

    public FollowPathSequenceCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        RobotMovement nextDriveMovement = bot.drivetrain.pathSequenceFollower.followPathSequence();
        new TeleOpDriveCommand(bot.drivetrain, new Vec2d(nextDriveMovement.translation.x, -nextDriveMovement.translation.y),
                -nextDriveMovement.rotation,
                1);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
