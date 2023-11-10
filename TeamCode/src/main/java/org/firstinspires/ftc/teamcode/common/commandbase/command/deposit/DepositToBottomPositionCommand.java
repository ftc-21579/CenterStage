package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DepositToBottomPositionCommand extends CommandBase {

    private Bot bot;

    public DepositToBottomPositionCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        bot.deposit.toBottomPosition();
    }

    @Override
    public boolean isFinished() {
        return this.bot.deposit.state == DepositState.BOTTOM;
    }
}
