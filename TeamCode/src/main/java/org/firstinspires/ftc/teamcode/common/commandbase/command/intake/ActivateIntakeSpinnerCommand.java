package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class ActivateIntakeSpinnerCommand extends InstantCommand {
    public ActivateIntakeSpinnerCommand(Intake intake) {
        super(
                () -> intake.activate()
        );
    }
}
