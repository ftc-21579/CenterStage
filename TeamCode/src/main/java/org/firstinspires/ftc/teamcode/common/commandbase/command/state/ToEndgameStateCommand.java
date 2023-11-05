package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToEndgameStateCommand extends InstantCommand {
    public ToEndgameStateCommand(Bot b) {
        super(
                () -> b.toEndgameState()
        );
    }
}
