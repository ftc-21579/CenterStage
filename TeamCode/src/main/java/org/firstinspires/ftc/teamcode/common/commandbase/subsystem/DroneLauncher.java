package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DroneLauncher {
    Bot bot;
    Servo releaseServo;

    public DroneLauncher(Bot bot) {
        this.bot = bot;
        releaseServo = bot.hMap.servo.get("droneServo");
        releaseServo.setPosition(0.25);
    }

    public void launch() {
        releaseServo.setPosition(0.0);
    }

    public void retract() {
        releaseServo.setPosition(0.25);
    }
}
