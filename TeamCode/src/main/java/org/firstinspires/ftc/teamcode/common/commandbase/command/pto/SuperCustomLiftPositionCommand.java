package org.firstinspires.ftc.teamcode.common.commandbase.command.pto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

public class SuperCustomLiftPositionCommand extends CommandBase {
    PTO pto;
    double leftTarget;
    double rightTarget;

    /**
     * This command is used to set the lift to a specific position
     * @param pto the PTO subsystem
     * @param leftTarget the left target position (inches)
     * @param rightTarget the right target position (inches)
     */
    public SuperCustomLiftPositionCommand(PTO pto, double leftTarget, double rightTarget) {
        this.pto = pto;
        this.leftTarget = leftTarget;
        this.rightTarget = rightTarget;
    }

    @Override
    public void initialize() {
        // left = target
        // right = target * -1
        // the right motor is inverted to spin the correct differential

        pto.setMotors(Configs.liftSpeed, leftTarget, rightTarget);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}