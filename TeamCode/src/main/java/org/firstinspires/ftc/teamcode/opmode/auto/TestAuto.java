package org.firstinspires.ftc.teamcode.opmode.auto;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.mineinjava.quail.odometry.path;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.FollowPathCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.UpdateLocalizerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name = "TestAuto - Square")
public class TestAuto extends LinearOpMode {

    Bot bot;
    Drivetrain drivetrain;
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
    public void runOpMode() throws InterruptedException {
        bot = new Bot(telemetry, hardwareMap);
        drivetrain = bot.drivetrain;
        drivetrain.setPath(p);

        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler s = CommandScheduler.getInstance();

            s.schedule(new UpdateLocalizerCommand(drivetrain));
            s.schedule(new FollowPathCommand(drivetrain, p));

            s.run();
        }
    }
}