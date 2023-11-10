package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class ManualLiftDownCommand extends InstantCommand {
    public ManualLiftDownCommand(Deposit d) {
        super(
                () -> d.lowerLift()
        );
    }
}
