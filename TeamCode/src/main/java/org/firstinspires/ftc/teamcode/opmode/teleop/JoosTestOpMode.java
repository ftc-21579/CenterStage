package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.DiffyBot;

@TeleOp(name = "JoosTestOpMode")
public class JoosTestOpMode extends CommandOpMode {

    @Override
    public void preInit() {
        DiffyBot robot = registerRobot(new DiffyBot(telem));

        telem.addLine("Ready?");
    }
}