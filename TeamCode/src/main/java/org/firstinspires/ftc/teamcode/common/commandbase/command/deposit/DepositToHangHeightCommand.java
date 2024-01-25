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
    public void initialize() {

    }

    @Override
    public void execute() {
        deposit.setLiftTarget(Configs.liftHangHeightPosition);

        if (
                deposit.getLiftPosition() > Configs.liftHangHeightPosition - 0.05 ||
                        deposit.getLiftPosition() < Configs.liftHangHeightPosition + 0.05
        ) {
            deposit.setLiftState(DepositState.HANG);
        }
    }

    @Override
    public boolean isFinished() {
        return this.deposit.state == DepositState.HANG;
    }
}
