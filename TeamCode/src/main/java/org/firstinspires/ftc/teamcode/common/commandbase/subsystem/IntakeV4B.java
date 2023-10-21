package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@Config
public class IntakeV4B {

    Bot bot;

    private Servo leftServo, rightServo;

    public IntakeV4B(Bot bot) {
        this.bot = bot;
    }

    public Command intakePosition() {
        return new InstantCommand(() -> {
            leftServo.setPosition(1.0);
            rightServo.setPosition(0.0);
        });
    }

    public Command transferPosition() {
        return new InstantCommand(() -> {
            leftServo.setPosition(0.0);
            rightServo.setPosition(1.0);
        });
    }

    public Command init() {
        return new InstantCommand(() -> {
            leftServo = bot.hMap.get(Servo.class, "leftV4BServo");
            rightServo = bot.hMap.get(Servo.class, "rightV4BServo");
        });
    }
}
