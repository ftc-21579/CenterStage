package org.firstinspires.ftc.teamcode.common.commandbase.command.pto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

public class CustomExtensionPositionCommand extends CommandBase {
    PTO pto;
    double target;

    /**
     * This command is used to set the extension to a specific position
     * @param pto the PTO subsystem
     * @param target the target position (inches)
     */
    public CustomExtensionPositionCommand(PTO pto, double target) {
        this.pto = pto;
        this.target = target;
    }

    @Override
    public void initialize() {
        // left = target
        // right = target
        // the extension is easy and both motors are the same

        pto.setMotors(Configs.extensionSpeed,
                (target * Configs.EXTENSION_TICKS_PER_INCH),
                (target * Configs.EXTENSION_TICKS_PER_INCH));
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
