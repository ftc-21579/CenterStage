package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToggleLeftPixelCommand extends InstantCommand {
    public DepositToggleLeftPixelCommand(Deposit d) {
        super(
                () -> d.toggleLeftPixelServo()
        );
    }
}
