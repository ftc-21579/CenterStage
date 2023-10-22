package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Config
public class Intake {

    Bot bot;

    private CRServo intakeServo;
    private ColorSensor leftSensor, rightSensor;

    enum intakeState {
        ACTIVE,
        IDLE
    }

    private intakeState state = intakeState.IDLE;

    public Intake(Bot bot) {
        this.bot = bot;
    }

    public Command activate() {
        return new InstantCommand(() -> {
            state = intakeState.ACTIVE;
            intakeServo.setPower(1);
        });
    }

    public Command disable() {
        return new InstantCommand(() -> {
            state = intakeState.IDLE;
            intakeServo.setPower(0);
        });
    }

    public Command toggleState() {
        return new InstantCommand(() -> {
            if (state == intakeState.ACTIVE) {
                bot.schedule(disable());
            } else {
                bot.schedule(activate());
            }
        });
    }

    public ArrayList<PixelColor> getPixelColors() {
        ArrayList<Integer> leftRGB = new ArrayList<>(Arrays.asList(leftSensor.red(), leftSensor.green(), leftSensor.blue()));
        //ArrayList<Integer> rightRGB = new ArrayList<>(Arrays.asList(rightSensor.red(), rightSensor.green(), rightSensor.blue()));

        ArrayList<PixelColor> colors = new ArrayList<>();

        if (leftRGB.get(0) > 600 && leftRGB.get(0) < 900 && leftRGB.get(1) > 2600 && leftRGB.get(1) < 2800 && leftRGB.get(2) > 1050 && leftRGB.get(2) < 1300) {
            colors.add(PixelColor.GREEN);
        } else if (leftRGB.get(0) > 4400 && leftRGB.get(0) < 4600 && leftRGB.get(1) > 8800 && leftRGB.get(1) < 9000 && leftRGB.get(2) > 9600 && leftRGB.get(2) < 9800) {
            colors.add(PixelColor.WHITE);
        } else if (leftRGB.get(0) > 2100 && leftRGB.get(0) < 2300 && leftRGB.get(1) > 3500 && leftRGB.get(1) < 3700 && leftRGB.get(2) > 6500 && leftRGB.get(2) < 6700) {
            colors.add(PixelColor.PURPLE);
        } else if (leftRGB.get(0) > 2450 && leftRGB.get(0) < 2600 && leftRGB.get(1) > 3900 && leftRGB.get(1) < 4100 && leftRGB.get(2) > 1100 && leftRGB.get(2) < 1300) {
            colors.add(PixelColor.YELLOW);
        } else {
            colors.add(PixelColor.NONE);
        }

        colors.add(PixelColor.NONE);

        bot.telem.addData("Colors", "Left: " + colors.get(0) + " Right: " + colors.get(1));
        return colors;
    }

    public Command init() {
        return new InstantCommand(() -> {
            intakeServo = bot.hMap.get(CRServo.class, "intakeServo");

            leftSensor = bot.hMap.get(ColorSensor.class, "leftColorSensor");
            //rightSensor = bot.hMap.get(ColorSensor.class, "rightColorSensor");

            leftSensor.enableLed(true);
            //rightSensor.enableLed(true);
        });
    }
}
