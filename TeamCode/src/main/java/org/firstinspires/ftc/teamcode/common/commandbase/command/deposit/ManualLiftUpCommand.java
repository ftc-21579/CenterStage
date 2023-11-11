package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class ManualLiftUpCommand extends InstantCommand {
    public ManualLiftUpCommand(Deposit d) {
        super(
                () -> d.raiseLift()
        );
    }
}
