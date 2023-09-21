package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@TeleOp(name = "JoosTestOpMode")
public class JoosTestOpMode extends CommandOpMode {

    @Override
    public void preInit() {
        Bot robot = registerRobot(new Bot(telem));

        telem.addLine("Ready");

    }
}