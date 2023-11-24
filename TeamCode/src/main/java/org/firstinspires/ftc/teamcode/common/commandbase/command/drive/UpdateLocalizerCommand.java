package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;

public class UpdateLocalizerCommand extends InstantCommand {
    public UpdateLocalizerCommand(MecanumDrivetrain d) {
        super(
                () -> d.updateLocalizer()
        );
    }
}
