package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class AutomaticDepositCommand extends InstantCommand {
    public AutomaticDepositCommand(Bot b) {
        super(
                () -> {
                    new SequentialCommandGroup(
                            new DepositV4BToDepositCommand(b.deposit),
                            new DepositAutomaticHeightCommand(b),
                            new ReleasePixelsCommand(b.deposit)
                    );
                }
        );
    }
}
