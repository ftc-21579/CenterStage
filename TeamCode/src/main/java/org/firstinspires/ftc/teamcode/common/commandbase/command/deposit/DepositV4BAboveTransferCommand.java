package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BAboveTransferCommand extends CommandBase {

    private Deposit deposit;
    private boolean ready = false;
    public DepositV4BAboveTransferCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setLeftV4BPosition(Configs.leftV4bAboveTransferPosition);
        deposit.setRightV4BPosition(Configs.rightV4bAboveTransferPosition);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
