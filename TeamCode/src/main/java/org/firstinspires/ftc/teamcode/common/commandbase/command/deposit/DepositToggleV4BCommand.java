package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositToggleV4BCommand extends InstantCommand {
    public DepositToggleV4BCommand(Deposit d) {
        super(
                () -> d.v4bToggle()
        );
    }
}
