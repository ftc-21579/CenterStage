package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class ReleaseRightPixelCommand extends CommandBase {

    Deposit deposit;
    public ReleaseRightPixelCommand (Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setRightGripperPosition(Configs.rightGripperReleasePosition);
        deposit.bot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
