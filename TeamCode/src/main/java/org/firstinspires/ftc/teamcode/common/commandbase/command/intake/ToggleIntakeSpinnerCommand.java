package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class ToggleIntakeSpinnerCommand extends InstantCommand {
    public ToggleIntakeSpinnerCommand(Intake intake) {
        super(
                () -> intake.toggleState()
        );
    }

}
