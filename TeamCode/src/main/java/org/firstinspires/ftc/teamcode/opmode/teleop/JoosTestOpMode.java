package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="JoosTest")
public class JoosTestOpMode extends CommandOpMode {
    public SuperTelemetry telem = new SuperTelemetry();
    @Override
    public void preInit() {
        JoosTestBot robot = registerRobot(new JoosTestBot());

        telem.addLine("Ready?");
    }
}
