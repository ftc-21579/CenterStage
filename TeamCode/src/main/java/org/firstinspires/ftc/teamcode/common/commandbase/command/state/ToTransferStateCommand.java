package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositStopLiftCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToTransferStateCommand extends CommandBase {
    private Bot bot;
    private ElapsedTime timer;

    public ToTransferStateCommand(Bot bot) {
        this.bot = bot;
        timer = new ElapsedTime();
    }

    @Override
    public void initialize() {
        bot.telem.addLine("To Transfer State Init");
        timer.reset();
    }

    @Override
    public void execute() {
        new IntakeTransferPositionCommand(bot.intake).schedule();
        bot.telem.addLine("Intake to Transfer Done");
    }

    @Override
    public boolean isFinished() {
        switch(bot.getBotState()) {
            case TRANSFER:
                return true;
            case INTAKE:
                if (timer.milliseconds() < 1250) {
                    return false;
                }
                new DepositToTransferPositionCommand(bot).schedule();
                if (timer.milliseconds() < 1750) {
                    return false;
                }
                new DisableIntakeSpinnerCommand(bot.intake).schedule();
                if (timer.milliseconds() < 2250) {
                    return false;
                }
                new GrabPixelsCommand(bot.deposit).schedule();
                return true;
            case DEPOSIT:
                if (timer.milliseconds() < 1250) {
                    return false;
                }
                new DepositToTransferPositionCommand(bot).schedule();
                if (timer.milliseconds() < 2250) {
                    return false;
                }
                new GrabPixelsCommand(bot.deposit).schedule();
                if (bot.deposit.state == DepositState.TRANSFER) {
                    bot.toTransferState();
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

}
