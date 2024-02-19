package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class HeadingServoPowerCommand extends InstantCommand {
    public HeadingServoPowerCommand(Intake intake, double power) {
        super(
                () -> intake.setHeadingServoPower(power)
        );
    }
}
