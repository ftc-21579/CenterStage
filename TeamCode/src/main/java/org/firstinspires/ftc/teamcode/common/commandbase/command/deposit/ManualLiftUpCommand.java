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
        deposit.pto.liftUp(0.5);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

