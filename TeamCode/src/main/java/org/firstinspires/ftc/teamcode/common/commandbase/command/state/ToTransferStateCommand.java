package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToTransferCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ManualLiftDownCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.opmode.teleop.TeleOp;

public class ToTransferStateCommand extends CommandBase {
    private Bot bot;
    private ElapsedTime timer = new ElapsedTime();
    private boolean ready = false;

    public ToTransferStateCommand(Bot bot) {
        this.bot = bot;
        timer.reset();
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        if (bot.getBotState() == BotState.TRANSFER) {
            ready = true;
        } else if (bot.getBotState() == BotState.INTAKE) {
            new ReleasePixelsCommand(bot.deposit).schedule();
            new DepositToBottomPositionCommand(bot).schedule();
            new IntakeTransferPositionCommand(bot.intake).schedule();

            if (timer.milliseconds() > 1000) {
                new DisableIntakeSpinnerCommand(bot.intake).schedule();
                new DepositV4BToTransferCommand(bot.deposit).schedule();
                bot.telem.addLine(">1000");
            }

            if (timer.milliseconds() > 1500) {
                new DepositToTransferPositionCommand(bot).schedule();
                bot.telem.addLine(">1500");
            }

            if (timer.milliseconds() > 2200) {
                new GrabPixelsCommand(bot.deposit).schedule();
                ready = true;
            }
        } else if (bot.getBotState() == BotState.DEPOSIT) {
            new DepositToTransferPositionCommand(bot).schedule();
            new DepositV4BToDepositCommand(bot.deposit).schedule();
            ready = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (ready) {bot.toTransferState();}
        return ready;
    }

}
