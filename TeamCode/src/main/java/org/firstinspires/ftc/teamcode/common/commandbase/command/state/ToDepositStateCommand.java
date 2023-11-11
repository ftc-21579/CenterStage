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
        bot.telem.addLine("To Deposit State Init");
    }

    @Override
    public void execute() {
        if (bot.getBotState() != BotState.TRANSFER) {
            bot.telem.addLine("To Deposit State Exec (NOT TRANSFER)");
            new ToTransferStateCommand(bot).schedule();
        } else {
            bot.telem.addLine("To Deposit State Exec");
            new DepositToBottomPositionCommand(bot).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        if(bot.deposit.state == DepositState.BOTTOM && bot.getBotState() == BotState.TRANSFER) {
            bot.toDepositState();
            bot.telem.addLine("To Deposit State Finished");
            return true;
        } else {
            return false;
        }
    }
}
