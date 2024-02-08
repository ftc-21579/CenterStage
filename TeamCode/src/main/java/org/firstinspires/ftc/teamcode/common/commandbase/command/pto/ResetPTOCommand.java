package org.firstinspires.ftc.teamcode.common.commandbase.command.pto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.PTO;

public class ResetPTOCommand extends CommandBase {
    PTO pto;
    public ResetPTOCommand(PTO pto) {
        this.pto = pto;
    }

    @Override
    public void initialize() {
        pto.setMotors((Configs.liftSpeed + Configs.extensionSpeed) / 2, 0, 0);
    }
    @Override
    public boolean isFinished() {
        // if the motor positions are less than 5, close enough to reset position (assumption ofc)
        return (Math.abs(pto.getPositions()[0]) <= 5 && Math.abs(pto.getPositions()[1]) <= 5);
    }
}
