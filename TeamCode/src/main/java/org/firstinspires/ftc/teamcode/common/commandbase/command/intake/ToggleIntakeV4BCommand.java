package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class ToggleIntakeV4BCommand extends InstantCommand {
    public ToggleIntakeV4BCommand(Intake intake) {
        super(
                () -> {

                        if (intake.getV4BPosition() == Configs.intakeIntakePosition) {
                            intake.v4bCustomHeight(Configs.intakeTransferPosition);
                        } else {
                            intake.v4bCustomHeight(Configs.intakeIntakePosition);
                        }
                }
        );
    }
}
