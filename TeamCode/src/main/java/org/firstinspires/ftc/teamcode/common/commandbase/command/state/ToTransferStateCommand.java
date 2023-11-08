package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ToTransferStateCommand extends CommandBase {
    Bot bot;
    ElapsedTime timer;
    public ToTransferStateCommand(Bot bot) {
        this.bot = bot;
        timer = new ElapsedTime();
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        new IntakeTransferPositionCommand(bot.intake).schedule();
    }

    @Override
    public boolean isFinished() {
        if (timer.milliseconds() < 1750) {
            return false;
        } else {
            new DisableIntakeSpinnerCommand(bot.intake).schedule();
            bot.toTransferState();
            return true;
        }
    }

}
