package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;
import com.amarcolini.joos.hardware.CRServo;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@Config
public class Intake {

    Bot bot;

    private CRServo intakeServo;

    public Intake(Bot bot) {
        this.bot = bot;
    }

    public Command activate() {
        return new InstantCommand(() -> {
            intakeServo.setPower(1);
        });
    }

    public Command disable() {
        return new InstantCommand(() -> {
            intakeServo.setPower(0);
        });
    }

    public void init() {
        intakeServo = bot.hMap.get(CRServo.class, "intakeServo");
    }
}
