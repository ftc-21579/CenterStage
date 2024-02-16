package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToggleRightPixelCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public DepositToggleRightPixelCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        if (deposit.getRightGripperPosition() == Configs.rightGripperGrabPosition) {
            deposit.setRightGripperPosition(Configs.rightGripperReleasePosition);
        } else {
            deposit.setRightGripperPosition(Configs.rightGripperGrabPosition);
        }
        deposit.bot.blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
