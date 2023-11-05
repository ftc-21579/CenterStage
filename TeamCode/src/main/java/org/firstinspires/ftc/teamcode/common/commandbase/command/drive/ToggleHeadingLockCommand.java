package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;

public class ToggleHeadingLockCommand extends InstantCommand {
    public ToggleHeadingLockCommand(DifferentialSwerveDrivetrain d) {
        super(
                () -> d.toggleHeadingLock()
        );
    }
}
