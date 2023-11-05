package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;

public class RotateHeadingLockCommand extends InstantCommand {
    public RotateHeadingLockCommand(DifferentialSwerveDrivetrain d) {
        super(
                () -> d.rotateHeadingLock()
        );
    }
}
