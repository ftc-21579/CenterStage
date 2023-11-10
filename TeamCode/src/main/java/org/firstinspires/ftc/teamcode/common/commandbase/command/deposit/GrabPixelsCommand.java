package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class GrabPixelsCommand extends InstantCommand {
    public GrabPixelsCommand(Deposit d) {
        super(() -> {
            if (d.state == DepositState.TRANSFER) {
                d.grabPixels();
            }
        });
    }
}
