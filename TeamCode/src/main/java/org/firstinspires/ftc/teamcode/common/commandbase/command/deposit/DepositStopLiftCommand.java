package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DepositStopLiftCommand extends InstantCommand {
    public DepositStopLiftCommand(Deposit d) {
        super(
                () -> d.stopLift()
        );
    }
}
