package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToggleRightPixelCommand extends InstantCommand {
    public DepositToggleRightPixelCommand(Deposit d) {
        super(
                () -> d.toggleRightPixelServo()
        );
    }
}