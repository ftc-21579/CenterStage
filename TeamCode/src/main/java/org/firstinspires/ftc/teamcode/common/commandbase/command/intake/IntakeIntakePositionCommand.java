package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class IntakeIntakePositionCommand extends InstantCommand {
    public IntakeIntakePositionCommand(Intake intake) {
        super(
                () -> intake.v4bIntakeState()
        );
    }
}
