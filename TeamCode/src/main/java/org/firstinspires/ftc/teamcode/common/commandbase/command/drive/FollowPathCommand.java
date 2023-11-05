package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.mineinjava.quail.odometry.path;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;

public class FollowPathCommand extends InstantCommand {
    public FollowPathCommand(DifferentialSwerveDrivetrain d, path p) {
        super(
                () -> d.followPath(p)
        );
    }
}
