package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.Util;
public class ManualLiftDownCommand extends CommandBase {
    private Deposit deposit;

    public ManualLiftDownCommand(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        deposit.setLiftTarget(Util.clamp(deposit.getLiftTarget() - 0.25, 0.0, 22.0));
        deposit.setLiftState(DepositState.MANUAL_LIFTING);
    }

    @Override
    public boolean isFinished() {
        return this.deposit.state == DepositState.MANUAL_LIFTING;
    }
}