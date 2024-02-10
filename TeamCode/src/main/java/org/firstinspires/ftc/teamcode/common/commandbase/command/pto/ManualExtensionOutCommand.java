package org.firstinspires.ftc.teamcode.common.commandbase.command.pto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

public class ManualExtensionOutCommand extends CommandBase {

    PTO pto;
    double multiplier;

    public ManualExtensionOutCommand(PTO pto, double multiplier) {
        this.pto = pto;
        this.multiplier = multiplier;
    }

    @Override
    public void initialize() {
        int[] currentPositions = pto.getPositions();

        // left = leftCurrent + increment
        // right = rightCurrent + increment
        // extension is easy and both motors are the same

        int newLeftTarget = (int) (currentPositions[0] +
                (Configs.extensionIncrement * Configs.EXTENSION_TICKS_PER_INCH));
        int newRightTarget = (int) (currentPositions[1] +
                (Configs.extensionIncrement * Configs.EXTENSION_TICKS_PER_INCH));

        // bounds checking to avoid skill issues
        if (pto.targetPosition + Configs.extensionIncrement > Configs.extensionMaxPos) {
            return;
        }

        pto.setMotors(Configs.extensionSpeed * multiplier, newLeftTarget, newRightTarget);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
