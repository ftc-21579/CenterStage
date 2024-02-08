package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class IntakeIncrementCommand extends InstantCommand {
    public IntakeIncrementCommand(Intake intake) {
        super(
                () -> {

                    intake.v4bCustomHeight(intake.getV4BPosition() + 0.1);

                }
        );
    }
}