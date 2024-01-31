package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class IntakeDecrementCommand extends InstantCommand {
    public IntakeDecrementCommand(Intake intake) {
        super(
                () -> intake.v4bDecrement()
        );
    }
}