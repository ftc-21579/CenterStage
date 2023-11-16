package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositStopLiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToTransferStateCommand extends CommandBase {
    private Bot bot;
    private boolean ready = false;

    public ToTransferStateCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {
        if (bot.getBotState() == BotState.TRANSFER) {
            ready = true;
        } else if (bot.getBotState() == BotState.INTAKE) {
            new ReleasePixelsCommand(bot.deposit).schedule();

            new IntakeTransferPositionCommand(bot.intake).withTimeout(1000).schedule();
            new DisableIntakeSpinnerCommand(bot.intake).schedule();

            new DepositToTransferPositionCommand(bot).withTimeout(1000).schedule();
            new GrabPixelsCommand(bot.deposit).schedule();
            ready = true;
        } else if (bot.getBotState() == BotState.DEPOSIT) {
            new DepositToBottomPositionCommand(bot).schedule();
            ready = true;
        }
    }

    @Override
    public void execute() {
        bot.telem.addLine("To Transfer State Execute");
    }

    @Override
    public boolean isFinished() {
        return ready;
    }

}
