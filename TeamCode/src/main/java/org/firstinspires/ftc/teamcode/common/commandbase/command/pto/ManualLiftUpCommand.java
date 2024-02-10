package org.firstinspires.ftc.teamcode.common.commandbase.command.pto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

public class ManualLiftUpCommand extends CommandBase {

    PTO pto;
    double multiplier;

    public ManualLiftUpCommand(PTO pto, double multiplier) {
        this.pto = pto;
        this.multiplier = multiplier;
    }

    @Override
    public void initialize() {
        int[] currentPositions = pto.getPositions();

        // left = leftCurrent - increment
        // right = (rightCurrent + increment) * -1
        // the right motor is inverted to spin the correct differential

        int newLeftTarget = (int) (currentPositions[0] +
                (Configs.liftIncrement * Configs.LIFT_TICKS_PER_INCH));
        int newRightTarget = (int) (currentPositions[1] -
                (Configs.liftIncrement * Configs.LIFT_TICKS_PER_INCH));

        // bounds checking to avoid skill issues
        if (currentPositions[0] + Configs.liftIncrement > Configs.liftMaxPos) {
            return;
        }

        pto.setMotors(Configs.liftSpeed * multiplier, newLeftTarget, newRightTarget);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
