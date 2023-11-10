package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

public class DroneLauncher {
    Bot bot;
    Servo releaseServo;

    public DroneLauncher(Bot bot) {
        this.bot = bot;
        this.releaseServo = bot.hMap.servo.get("droneServo");
        this.releaseServo.setPosition(0.25);
    }

    public void launch() {
        this.releaseServo.setPosition(0.0);
    }

    public void reset() {
        this.releaseServo.setPosition(0.25);
    }
}
