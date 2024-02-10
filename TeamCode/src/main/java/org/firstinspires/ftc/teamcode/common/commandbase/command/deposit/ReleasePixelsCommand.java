package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class ReleasePixelsCommand extends CommandBase {
    private Deposit deposit;
    private boolean ready = false;
    public ReleasePixelsCommand(Deposit d) {
        this.deposit = d;
    }

    @Override
    public void execute() {
        deposit.setLeftGripperPosition(Configs.leftGripperReleasePosition);
        deposit.setRightGripperPosition(Configs.rightGripperReleasePosition);

        ready = true;
    }

    @Override
    public boolean isFinished() {
        return ready;
    }
}
