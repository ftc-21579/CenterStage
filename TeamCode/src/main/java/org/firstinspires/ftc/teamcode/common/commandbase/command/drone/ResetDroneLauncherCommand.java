package org.firstinspires.ftc.teamcode.common.commandbase.command.drone;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;

public class ResetDroneLauncherCommand extends InstantCommand {
    public ResetDroneLauncherCommand(DroneLauncher launcher) {
        super(
                () -> launcher.reset()
        );
    }
}
