package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class DisableIntakeSpinnerCommand extends InstantCommand {
    public DisableIntakeSpinnerCommand(Intake intake) {
        super(
                () -> {

                    intake.setSpinnerPower(0.0);

                }
        );
    }
}
