package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToHangHeightCommand extends InstantCommand {
    public DepositToHangHeightCommand(Deposit d) {
        super(
                () -> d.toHangHeight()
        );
    }
}
