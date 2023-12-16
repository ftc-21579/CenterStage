package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class DoAPullUpCommand extends InstantCommand {
    public DoAPullUpCommand(Deposit d) {
        super(
                () -> d.doAPullUp()
        );
    }
}
