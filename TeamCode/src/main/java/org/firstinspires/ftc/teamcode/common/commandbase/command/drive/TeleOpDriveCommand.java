package org.firstinspires.ftc.teamcode.common.commandbase.command.drive;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.mineinjava.quail.util.geometry.Vec2d;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;

public class TeleOpDriveCommand extends InstantCommand {
    public TeleOpDriveCommand(Drivetrain drivetrain, Vec2d leftStick, double rot) {
        super(
                () -> drivetrain.teleopDrive(leftStick, rot)
        );
    }
}
