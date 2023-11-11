package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

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
            bot.toEndgameState();
        }
    }

    @Override
    public boolean isFinished() {
        return bot.getBotState() == BotState.ENDGAME;
    }
}
