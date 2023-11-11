package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.DifferentialSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class Autonomous extends LinearOpMode {

    Bot bot;
    DifferentialSwerveDrivetrain drivetrain;

    GamepadEx gamepad;

    ArrayList<String[]> paths = new ArrayList<>();
    public int currentPath = 0;

    @Override
    public void runOpMode() {

        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        filePath += "auto.xml";
        Properties pathsProperties = new Properties();

        try {
            pathsProperties.loadFromXML(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<?> e = pathsProperties.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = pathsProperties.getProperty(key);
            paths.add(new String[] {key, value});
        }

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        bot = new Bot(telemetry, hardwareMap);
        drivetrain = bot.drivetrain;

        gamepad = new GamepadEx(gamepad1);

        telemetry.addData("Current Path", paths.get(currentPath)[0]);
        telemetry.update();

        while (!isStarted()) {
            gamepad.readButtons();

            if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                currentPath++;
                if (currentPath >= paths.size()) {
                    currentPath = 0;
                }

                telemetry.addData("Current Path", paths.get(currentPath)[0]);
                telemetry.update();
            }

        }

    }
}
