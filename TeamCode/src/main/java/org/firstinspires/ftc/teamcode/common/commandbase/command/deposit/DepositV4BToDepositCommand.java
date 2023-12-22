package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositV4BToDepositCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public DepositV4BToDepositCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setLeftV4BPosition(Configs.leftV4bDepositPosition);
        deposit.setRightV4BPosition(Configs.rightV4bDepositPosition);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
