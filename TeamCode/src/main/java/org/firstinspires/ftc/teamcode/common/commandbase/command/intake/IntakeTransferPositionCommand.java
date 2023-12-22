package org.firstinspires.ftc.teamcode.common.commandbase.command.intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Configs;
import org.firstinspires.ftc.teamcode.common.Util;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Intake;

public class IntakeTransferPositionCommand extends CommandBase {
    private Intake intake;
    private ElapsedTime timer = new ElapsedTime();
    private Boolean ready = false;

    public IntakeTransferPositionCommand(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        double instantTargetPosition = Util.motion_profile(
                Configs.intakeV4BMaxAccel,
                Configs.intakeV4BMaxVelo,
                1.0,
                timer.seconds());

        if (instantTargetPosition == 1.0) {
            ready = true;
        }

        intake.setIntakeV4BPosition(instantTargetPosition);
    }

    @Override
    public boolean isFinished() {
        return ready = false;
    }
}
