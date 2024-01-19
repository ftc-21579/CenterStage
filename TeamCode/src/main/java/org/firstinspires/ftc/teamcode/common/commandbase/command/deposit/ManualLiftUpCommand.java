package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.Util;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ManualLiftUpCommand extends CommandBase {

    private Deposit deposit;

    public ManualLiftUpCommand(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        deposit.setLiftTarget(Util.clamp(deposit.getLiftTarget() + 0.25, 0.0, 22.0));
        deposit.setLiftState(DepositState.MANUAL_LIFTING);

        if (deposit.getLiftPosition() >= 5.0) {
            new DepositV4BToDepositCommand(deposit).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return this.deposit.state == DepositState.MANUAL_LIFTING;
    }
}
