package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class RunLiftPIDCommand extends InstantCommand {
    public RunLiftPIDCommand(Deposit d) {
        super(
                () -> d.runLiftPID()
        );
    }
}
