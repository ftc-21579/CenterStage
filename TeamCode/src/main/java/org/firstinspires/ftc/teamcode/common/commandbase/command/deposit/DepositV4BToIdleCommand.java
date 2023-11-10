package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BToIdleCommand extends InstantCommand {
    public DepositV4BToIdleCommand(Deposit d) {
        super(
                () -> d.v4bToIdle()
        );
    }
}
