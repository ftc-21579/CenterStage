package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import static org.firstinspires.ftc.teamcode.common.drive.drive.Bot.fieldCentric;

import com.amarcolini.joos.command.BasicCommand;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.geometry.Vector2d;
import com.mineinjava.quail.robotMovement;
import com.mineinjava.quail.util.Vec2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.teamcode.common.drive.geometry.Pose;

public class Drivetrain {
    Bot bot;

    /**
     * Encapsulates the drivetrain subsystem commands
     * @param bot
     */
    public Drivetrain(Bot bot) {
        this.bot = bot;
    }

    /** The standard drive command for teleop, supports field centric if fieldCentric
     * @return Command
     */
    public Command teleopDrive() {
        return new BasicCommand(() -> {
            Vector2d leftStick = bot.gamepad().p1.getLeftStick();
            double x = -leftStick.x;
            double y = -leftStick.y;
            double rot = -bot.gamepad().p1.getRightStick().x;

            double botHeading = bot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            if (fieldCentric) {
                bot.drive.move(new robotMovement(rot, new Vec2d(y, x)), -botHeading);
            } else {
                bot.drive.move(new robotMovement(rot, new Vec2d(y, x)), 0);
            }
        });
    }

    public Command updateLocalizer() {
        return new BasicCommand(() -> {
            bot.localizer.periodic();

            Pose current = bot.localizer.getPos();

            bot.telem.addData("Pose X", current.x);
            bot.telem.addData("Pose Y", current.y);
            bot.telem.addData("Pose Heading", current.heading);
        });
    }
}
