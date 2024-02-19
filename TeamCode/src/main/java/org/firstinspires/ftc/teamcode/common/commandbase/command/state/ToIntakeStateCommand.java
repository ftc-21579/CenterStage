package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ActivateIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeIntakePositionCommand;
import org.firstinspires.ftc.teamcode.common.Bot;

public class ToIntakeStateCommand extends CommandBase {
    private Bot bot;

    public ToIntakeStateCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot.telem.addLine("To Intake State Init");
    }

    @Override
    public void execute() {
        if(bot.getBotState() != BotState.TRANSFER) {
            bot.telem.addLine("To Intake State Exec (NOT TRANSFER)");
            new ReleasePixelsCommand(bot.deposit);
            new ToTransferStateCommand(bot).schedule();
            bot.telem.addLine("To Intake State Exec (NOT TRANSFER) (DONE)");
        } else {
            bot.telem.addLine("To Intake State Exec");
            new DepositV4BToDepositCommand(bot.deposit).schedule();
            new ActivateIntakeSpinnerCommand(bot.intake).schedule();
            new IntakeIntakePositionCommand(bot.intake).schedule();
            bot.toIntakeState();
        }
    }

    @Override
    public boolean isFinished() {
        return bot.getBotState() == BotState.INTAKE;
    }
}
