package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class IntakeCustomHeightCommand extends CommandBase {
    private Bot bot;
    private double height = 0.0;

    public IntakeCustomHeightCommand(Bot bot, double height) {
        this.bot = bot;
        this.height = height;
    }

    @Override
    public void execute() {
        bot.intake.v4bCustomHeight(height);
    }

    @Override
    public boolean isFinished() {
        bot.toIntakeState();
        return true;
    }
}
