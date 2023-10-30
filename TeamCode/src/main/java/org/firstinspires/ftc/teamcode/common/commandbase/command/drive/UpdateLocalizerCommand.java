package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;

public class UpdateLocalizerCommand extends InstantCommand {
    public UpdateLocalizerCommand(Drivetrain d) {
        super(
                () -> d.updateLocalizer()
        );
    }
}
