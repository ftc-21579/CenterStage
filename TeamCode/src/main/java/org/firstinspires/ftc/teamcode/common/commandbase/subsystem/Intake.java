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
        //int rightReading = rightSensor.argb();

        //bot.telem.addData("Right Reading", rightReading);

        ArrayList<PixelColor> colors = new ArrayList<>();

        if (leftRGB.get(0) > 30 && leftRGB.get(0) < 39 && leftRGB.get(1) > 70 && leftRGB.get(1) < 80 && leftRGB.get(2) > 60 && leftRGB.get(2) < 75) {
            colors.add(PixelColor.GREEN);
        } else if (leftRGB.get(0) > 45 && leftRGB.get(0) < 55 && leftRGB.get(1) > 95 && leftRGB.get(1) < 105 && leftRGB.get(2) > 100 && leftRGB.get(2) < 110) {
            colors.add(PixelColor.WHITE);
        } else if (leftRGB.get(0) > 35 && leftRGB.get(0) < 45 && leftRGB.get(1) > 70 && leftRGB.get(1) < 80 && leftRGB.get(2) > 80 && leftRGB.get(2) < 90) {
            colors.add(PixelColor.PURPLE);
        } else if (leftRGB.get(0) > 35 && leftRGB.get(0) < 45 && leftRGB.get(1) > 70 && leftRGB.get(1) < 80 && leftRGB.get(2) > 60 && leftRGB.get(2) < 70) {
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

            ((SwitchableLight)leftSensor).enableLight(true);
            //rightSensor.enableLed(true);
        });
    }
}
