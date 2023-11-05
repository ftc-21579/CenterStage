package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToDepositStateCommand extends InstantCommand {
    public ToDepositStateCommand(Bot b) {
        super(
                () -> b.toDepositState()
        );
    }
}
