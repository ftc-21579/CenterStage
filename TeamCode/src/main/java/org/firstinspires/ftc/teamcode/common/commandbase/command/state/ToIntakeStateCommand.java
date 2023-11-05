package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToIntakeStateCommand extends InstantCommand {
    public ToIntakeStateCommand(Bot b) {
        super(
                () -> b.toIntakeState()
        );
    }
}
