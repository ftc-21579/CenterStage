package org.firstinspires.ftc.teamcode.common.commandbase.command.pto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

public class CustomLiftPositionCommand extends CommandBase {
    PTO pto;
    double target;

    /**
     * This command is used to set the lift to a specific position
     * @param pto the PTO subsystem
     * @param target the target position (inches)
     */
    public CustomLiftPositionCommand(PTO pto, double target) {
        this.pto = pto;
        this.target = target;
    }

    @Override
    public void initialize() {
        // left = target
        // right = target * -1
        // the right motor is inverted to spin the correct differential

        pto.setMotors(Configs.liftSpeed,
                (target * Configs.LIFT_TICKS_PER_INCH),
                (target * Configs.LIFT_TICKS_PER_INCH) * -1);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
