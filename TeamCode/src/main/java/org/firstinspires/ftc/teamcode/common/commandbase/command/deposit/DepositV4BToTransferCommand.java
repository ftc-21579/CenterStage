package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BToTransferCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public DepositV4BToTransferCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setLeftV4BPosition(Configs.leftV4bTransferPosition);
        deposit.setRightV4BPosition(Configs.rightV4bTransferPosition);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
