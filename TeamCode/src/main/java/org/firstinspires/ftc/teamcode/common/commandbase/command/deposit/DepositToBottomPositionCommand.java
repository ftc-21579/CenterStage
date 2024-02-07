package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DepositToBottomPositionCommand extends CommandBase {

    private PTO deposit;

    public DepositToBottomPositionCommand(PTO deposit) {
        this.deposit = deposit;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        deposit.liftUp(Configs.liftSpeed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}