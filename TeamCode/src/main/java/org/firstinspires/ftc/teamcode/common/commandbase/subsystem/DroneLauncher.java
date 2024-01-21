package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@Config
public class DroneLauncher {
    Bot bot;
    Servo releaseServo;
    public static double release_position = 0.3, reset_position = 0.0;

    public DroneLauncher(Bot bot) {
        this.bot = bot;
        this.releaseServo = bot.hMap.servo.get("droneServo");
        this.releaseServo.setPosition(0.0);
    }

    public void launch() {
        this.releaseServo.setPosition(release_position);
    }

    public void reset() {
        this.releaseServo.setPosition(reset_position);
    }
}
