package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BToDepositCommand extends InstantCommand {
    public DepositV4BToDepositCommand(Deposit d) {
        super(
                () -> d.v4bToDeposit()
        );
    }
}
