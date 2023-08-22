package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "JoosTestOpMode")
public class JoosTestOpMode extends CommandOpMode {

    @Override
    public void preInit() {
        DiffySwerve robot = registerRobot(new DiffySwerve(telem));

        telem.addLine("Ready?");
    }
}