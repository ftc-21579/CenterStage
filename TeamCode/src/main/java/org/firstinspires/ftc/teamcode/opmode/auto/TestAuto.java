package org.firstinspires.ftc.teamcode.opmode.auto;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.command.RepeatCommand;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.mineinjava.quail.odometry.path;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name = "TestAuto - Square")
public class TestAuto extends CommandOpMode {

    Bot bot;
    path p = new path(new ArrayList<Pose2d>(
            Arrays.asList(
                    new Pose2d(0, 0, 0),
                    new Pose2d(0, 10, 0),
                    new Pose2d(10, 10, 0),
                    new Pose2d(10, 0, 0),
                    new Pose2d(0, 0, 0)
            )
    ));


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