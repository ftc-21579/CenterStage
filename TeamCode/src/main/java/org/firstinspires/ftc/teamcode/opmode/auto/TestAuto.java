package org.firstinspires.ftc.teamcode.opmode.auto;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.command.RepeatCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.mineinjava.quail.odometry.path;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name = "TestAuto - Square")
public class TestAuto extends CommandOpMode {

    Bot bot;
    path p = new path(new ArrayList<double[]>(
            Arrays.asList(
                    new double[]{0, 0},
                    new double[]{0, 5},
                    new double[]{-5, 5},
                    new double[]{-5, 0},
                    new double[]{1, 0}
            )
    ), 0);


    @Override
    public void preInit() {
        bot = registerRobot(new Bot(telem));
        schedule(bot.setPath(p));
    }

    @Override
    public void preStart() {
        schedule(new RepeatCommand(bot.drivetrain.followPath(), -1));
    }
}