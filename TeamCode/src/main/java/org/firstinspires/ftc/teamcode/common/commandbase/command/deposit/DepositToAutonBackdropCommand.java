package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DepositToAutonBackdropCommand extends CommandBase {

    private Deposit deposit;

    public DepositToAutonBackdropCommand(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        deposit.setLiftTarget(Configs.liftBottomPosition);

        if (
                deposit.getLiftPosition() > Configs.liftAutonBackdropPosition - 0.05 ||
                        deposit.getLiftPosition() < Configs.liftAutonBackdropPosition + 0.05
        ) {
            deposit.setLiftState(DepositState.AUTONBACKDROP);
        }
    }

    @Override
    public boolean isFinished() {
        return this.deposit.state == DepositState.AUTONBACKDROP;
    }
}