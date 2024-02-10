package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToggleLeftPixelCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public DepositToggleLeftPixelCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        if (deposit.getLeftGripperPosition() == Configs.leftGripperGrabPosition) {
            deposit.setLeftGripperPosition(Configs.leftGripperReleasePosition);
        } else {
            deposit.setLeftGripperPosition(Configs.leftGripperGrabPosition);
        }
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
