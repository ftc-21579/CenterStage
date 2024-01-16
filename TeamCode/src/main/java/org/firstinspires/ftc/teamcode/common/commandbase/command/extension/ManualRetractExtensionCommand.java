package org.firstinspires.ftc.teamcode.common.commandbase.command.extension;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class ManualRetractExtensionCommand extends InstantCommand {
    public ManualRetractExtensionCommand(Bot bot) {
        super(
                () -> bot.pto.extend(0.5)
        );
    }
}
