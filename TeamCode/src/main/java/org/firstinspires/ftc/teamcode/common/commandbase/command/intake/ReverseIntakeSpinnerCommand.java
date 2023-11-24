package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class ReverseIntakeSpinnerCommand extends InstantCommand {
    public ReverseIntakeSpinnerCommand(Intake i) {
        super(
                () -> i.reverse()
        );
    }
}
