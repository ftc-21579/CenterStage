package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class ToggleIntakeV4BCommand extends InstantCommand {
    public ToggleIntakeV4BCommand(Intake intake) {
        super(
                () -> intake.v4bToggleState()
        );
    }
}
