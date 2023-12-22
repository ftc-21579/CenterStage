package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToHangHeightCommand extends CommandBase {
    private Deposit deposit;
    public DepositToHangHeightCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setLiftTarget(Configs.liftHangHeightPosition);
        deposit.setLiftState(DepositState.MANUAL_LIFTING);
    }
}
