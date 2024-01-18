package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToBottomPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToHangHeightCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositToTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToTransferCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ManualLiftDownCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeAboveTransferPositionCommand;
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

            bot.telem.addData("Time: ", timer.milliseconds());

            if (timer.milliseconds() < 1500) {
                new IntakeAboveTransferPositionCommand(bot.intake).schedule();
                new DepositToTransferPositionCommand(bot).schedule();
                new ReleasePixelsCommand(bot.deposit).schedule();
            } else if (timer.milliseconds() >= 1500 && timer.milliseconds() < 2500) {
                new IntakeTransferPositionCommand(bot.intake).schedule();
                new DepositV4BToTransferCommand(bot.deposit).schedule();
                bot.telem.addLine(">3000");
            } else {
                //new IntakeTransferPositionCommand(bot.intake).schedule();
                new DisableIntakeSpinnerCommand(bot.intake).schedule();
                new GrabPixelsCommand(bot.deposit).schedule();
                ready = true;
            }
            //new IntakeAboveTransferPositionCommand(bot.intake).schedule();
            //new DepositToTransferPositionCommand(bot).schedule();

            //if (timer.milliseconds() > 1500) {
                //new IntakeTransferPositionCommand(bot.intake).schedule();
                //new DepositV4BToTransferCommand(bot.deposit).schedule();
                //bot.telem.addLine(">3000");
            //} else {
                //new ReleasePixelsCommand(bot.deposit).schedule();
            //}

            //if (timer.milliseconds() > 2500) {
                //new DisableIntakeSpinnerCommand(bot.intake).schedule();
                //new GrabPixelsCommand(bot.deposit).schedule();
                //ready = true;
            //}
        } else if (bot.getBotState() == BotState.DEPOSIT || bot.getBotState() == BotState.ENDGAME) {
            //new DepositToTransferPositionCommand(bot).schedule();
            //new DepositV4BToDepositCommand(bot.deposit).schedule();
            ready = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (ready) {bot.toTransferState();}
        return ready;
    }

}
