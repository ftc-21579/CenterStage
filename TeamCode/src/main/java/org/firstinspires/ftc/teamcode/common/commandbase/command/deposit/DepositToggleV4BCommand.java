package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToggleV4BCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public DepositToggleV4BCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        if (deposit.getLeftV4BPosition() == Configs.leftV4bDepositPosition) {
            deposit.setLeftV4BPosition(Configs.leftV4bIdlePosition);
            deposit.setRightV4BPosition(Configs.rightV4bIdlePosition);
        } else {
            deposit.setLeftV4BPosition(Configs.leftV4bDepositPosition);
            deposit.setRightV4BPosition(Configs.rightV4bDepositPosition);
        }
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
