package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DepositToTransferPositionCommand extends CommandBase {

    Bot bot;

    public DepositToTransferPositionCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        bot.pto.liftToBottom(0.5);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
