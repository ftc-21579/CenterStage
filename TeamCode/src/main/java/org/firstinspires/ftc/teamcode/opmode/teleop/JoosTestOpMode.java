package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="JoosTest")
public class JoosTestOpMode extends CommandOpMode {

    @Override
    public void preInit() {
        JoosTestBot robot = registerRobot(new JoosTestBot());

        schedule(() -> {
            robot.drive.setDrivePower(new Pose2d(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x
            ));
        });

        telemetry.addLine("Ready?");
    }
}
