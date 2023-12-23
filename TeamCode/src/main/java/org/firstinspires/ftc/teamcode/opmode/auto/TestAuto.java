package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.mineinjava.quail.pathing.PathSequenceFollower;
import com.mineinjava.quail.util.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.mineinjava.quail.pathing.Path;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.FollowPathCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.FollowPathSequenceCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.drive.UpdateLocalizerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.ActivateIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name = "TestAuto - Square")
public class TestAuto extends LinearOpMode {

    Bot bot;
    MecanumDrivetrain drivetrain;
    Path a = new Path(new ArrayList<Pose2d>(
            Arrays.asList(
                    new Pose2d(0, 0, 0),
                    new Pose2d(0, 10, 0),
                    new Pose2d(10, 10, 0)
            )
    ));

    Path b = new Path(new ArrayList<Pose2d>(
            Arrays.asList(
                    new Pose2d(10, 10, 0),
                    new Pose2d(10, 0, 0),
                    new Pose2d(0, 0, 0)
            )
    ));

    Boolean fCentric = false;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        bot = new Bot(telemetry, hardwareMap);
        drivetrain = bot.drivetrain;
        bot.getLocalizer().setPose(new Pose2d(0, 0, 0));
        bot.getImu().resetYaw();

        if (!MecanumDrivetrain.fieldCentric) {
            fCentric = false;
            MecanumDrivetrain.fieldCentric = true;
        }

        drivetrain.pathSequenceFollower
                .addPath(a)
                .addDisplacementMarker(() -> new ActivateIntakeSpinnerCommand(bot.intake))
                .addPath(b);

        waitForStart();

        while (!drivetrain.pathFinished()) {
            CommandScheduler s = CommandScheduler.getInstance();

            s.schedule(new UpdateLocalizerCommand(drivetrain));
            s.schedule(new FollowPathSequenceCommand(drivetrain));

            telemetry.update();
            s.run();
        }

        if (!fCentric) {
            MecanumDrivetrain.fieldCentric = false;
        }
    }
}