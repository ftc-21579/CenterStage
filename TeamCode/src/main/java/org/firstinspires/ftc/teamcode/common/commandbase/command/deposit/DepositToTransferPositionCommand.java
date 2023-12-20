package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DepositToTransferPositionCommand extends CommandBase {

    Bot bot;

    public DepositToTransferPositionCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        bot.deposit.setLiftTarget(Configs.liftTransferPosition);

        if (
                bot.deposit.getLiftPosition() > Configs.liftTransferPosition - 0.05 ||
                bot.deposit.getLiftPosition() < Configs.liftTransferPosition + 0.5
        ) {
            bot.deposit.setLiftState(DepositState.TRANSFER);
        }
    }

    @Override
    public boolean isFinished() {
        return this.bot.deposit.state == DepositState.TRANSFER;
    }
}
