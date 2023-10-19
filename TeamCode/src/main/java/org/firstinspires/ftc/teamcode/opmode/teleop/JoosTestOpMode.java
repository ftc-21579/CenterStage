package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@TeleOp(name = "JoosTestOpMode")
public class JoosTestOpMode extends CommandOpMode {

    @Override
    public void preInit() {
        Bot robot = registerRobot(new Bot(telem));

        map(gamepad().p1.dpad_left::justActivated, robot.intakeState());
        map(gamepad().p1.dpad_up::justActivated, robot.transferState());

        telem.addLine("Ready");

    }
}