package org.firstinspires.ftc.teamcode.common.commandbase.command.extension;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;


public class ExtensionFullRetractCommand extends InstantCommand {
    public ExtensionFullRetractCommand(Bot bot) {
        super(
                () -> bot.pto.extensionToEndstops(0.5)
        );
    }
}
