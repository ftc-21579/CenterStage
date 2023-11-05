package org.firstinspires.ftc.teamcode.common.commandbase.command.drone;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DroneLauncher;

public class LaunchDroneCommand extends InstantCommand {
    public LaunchDroneCommand(DroneLauncher launcher) {
        super(
                () -> launcher.launch()
        );
    }
}
