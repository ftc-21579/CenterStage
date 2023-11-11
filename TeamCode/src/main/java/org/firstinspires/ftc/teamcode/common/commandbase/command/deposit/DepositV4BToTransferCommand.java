package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BToTransferCommand extends InstantCommand {
    public DepositV4BToTransferCommand(Deposit d) {
        super(
                () -> d.v4bToTransfer()
        );
    }
}
