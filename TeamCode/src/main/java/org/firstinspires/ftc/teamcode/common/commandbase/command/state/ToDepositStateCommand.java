package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToDepositStateCommand extends CommandBase {
    Bot bot;

    public ToDepositStateCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (bot.getBotState() != BotState.TRANSFER) {
            new ToTransferStateCommand(bot).schedule();
        } else {
            new DepositToBottomPositionCommand(bot).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        if(bot.deposit.state == DepositState.BOTTOM && bot.getBotState() == BotState.TRANSFER) {
            bot.toDepositState();
            return true;
        } else {
            return false;
        }
    }
}
