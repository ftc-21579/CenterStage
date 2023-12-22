package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

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
        if (deposit.leftGripper == GripperState.GRAB) {
            deposit.setRightGripperPosition(Configs.rightGripperReleasePosition);
            deposit.leftGripper = GripperState.RELEASE;
        } else {
            deposit.setRightGripperPosition(Configs.rightGripperGrabPosition);
            deposit.leftGripper = GripperState.GRAB;
        }
        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
