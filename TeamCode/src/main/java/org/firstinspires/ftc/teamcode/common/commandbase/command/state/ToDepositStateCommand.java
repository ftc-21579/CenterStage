package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToDepositStateCommand extends CommandBase {
    Bot bot;
    private ElapsedTime timer = new ElapsedTime();
    private boolean ready = false;

    public ToDepositStateCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot.telem.addLine("To Deposit State Init");
        ready = false;
        timer.reset();
    }

    @Override
    public void execute() {
        if (bot.getBotState() != BotState.TRANSFER) {
            bot.telem.addLine("To Deposit State Exec (NOT TRANSFER)");
            new ToTransferStateCommand(bot).schedule();
        } else {
            bot.telem.addLine("To Deposit State Exec");
            new DepositToBottomPositionCommand(bot.deposit).schedule();
            if (timer.milliseconds() > 1500) {
                new DepositV4BToDepositCommand(bot.deposit);
                ready = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (ready == true) {
            bot.toDepositState();
            //new DepositStopLiftCommand(bot.deposit).schedule();
            bot.telem.addLine("To Deposit State Finished");
            return true;
        } else {
            return false;
        }
    }
}
