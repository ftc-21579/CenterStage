package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DepositToTransferPositionCommand extends CommandBase {

    Bot bot;
    Boolean ready = false;

    public DepositToTransferPositionCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        bot.deposit.toTransferPosition();

        if (this.bot.deposit.state == DepositState.TRANSFER) {
            ready = true;
        }
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
