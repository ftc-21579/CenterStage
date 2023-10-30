package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.mineinjava.quail.odometry.path;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;

public class FollowPathCommand extends InstantCommand {
    public FollowPathCommand(Drivetrain d, path p) {
        super(
                () -> d.followPath(p)
        );
    }
}
