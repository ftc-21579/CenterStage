package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.Bot;

public class ToEndgameStateCommand extends CommandBase {
    private Bot bot;

    public ToEndgameStateCommand(Bot bot) {
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
            new DepositV4BToDepositCommand(bot.deposit);
            bot.toEndgameState();
        }
    }

    @Override
    public boolean isFinished() {
        return bot.getBotState() == BotState.ENDGAME;
    }
}
