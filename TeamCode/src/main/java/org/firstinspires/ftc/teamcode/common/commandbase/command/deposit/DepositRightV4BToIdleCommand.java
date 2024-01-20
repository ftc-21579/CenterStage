package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositRightV4BToIdleCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public DepositRightV4BToIdleCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setRightV4BPosition(Configs.rightV4bIdlePosition);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
