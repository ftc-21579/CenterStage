package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BToDropCommand extends InstantCommand {
    public DepositV4BToDropCommand(Deposit d) {
        super(
                () -> d.v4bToDrop()
        );
    }
}
