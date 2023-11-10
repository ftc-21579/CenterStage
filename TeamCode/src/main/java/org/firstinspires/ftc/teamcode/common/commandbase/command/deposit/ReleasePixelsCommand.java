package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;

public class ReleasePixelsCommand extends InstantCommand {
    public ReleasePixelsCommand(Deposit d) {
        super(() -> {
           if(d.state == DepositState.DEPOSIT_READY) {
                d.releasePixels();
           }
        });
    }
}
