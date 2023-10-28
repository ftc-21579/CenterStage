package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Config
public class Intake {

    Bot bot;

    private CRServo intakeServo;
    private ColorSensor leftSensor, rightSensor;

    public static int yellowRLower = 2200, yellowRUpper = 2800, yellowGLower = 3900, yellowGUpper = 4300, yellowBLower = 900, yellowBUpper = 1600;
    public static int purpleRLower = 1700, purpleRUpper = 2200, purpleGLower = 3100, purpleGUpper = 4000, purpleBLower = 6000, purpleBUpper = 7000;
    public static int greenRLower = 500, greenRUpper = 1000, greenGLower = 2500, greenGUpper = 2900, greenBLower = 950, greenBUpper = 1400;
    public static int whiteRLower = 4300, whiteRUpper = 4900, whiteGLower = 8700, whiteGUpper = 9300, whiteBLower = 9500, whiteBUpper = 10000;

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

        if (leftRGB.get(0) > greenRLower && leftRGB.get(0) < greenRUpper
                && leftRGB.get(1) > greenGLower && leftRGB.get(1) < greenGUpper
                && leftRGB.get(2) > greenBLower && leftRGB.get(2) < greenBUpper) {
            colors.add(PixelColor.GREEN);
        } else if (leftRGB.get(0) > whiteRLower && leftRGB.get(0) < whiteRUpper
                && leftRGB.get(1) > whiteGLower && leftRGB.get(1) < whiteGUpper
                && leftRGB.get(2) > whiteBLower && leftRGB.get(2) < whiteBUpper) {
            colors.add(PixelColor.WHITE);
        } else if (leftRGB.get(0) > purpleRLower && leftRGB.get(0) < purpleRUpper
                && leftRGB.get(1) > purpleGLower && leftRGB.get(1) < purpleGUpper
                && leftRGB.get(2) > purpleBLower && leftRGB.get(2) < purpleBUpper) {
            colors.add(PixelColor.PURPLE);
        } else if (leftRGB.get(0) > yellowRLower && leftRGB.get(0) < yellowRUpper
                && leftRGB.get(1) > yellowGLower && leftRGB.get(1) < yellowGUpper
                && leftRGB.get(2) > yellowBLower && leftRGB.get(2) < yellowBUpper) {
            colors.add(PixelColor.YELLOW);
        } else {
            colors.add(PixelColor.NONE);
        }

        //if (rightRGB.get(0) > greenRLower && rightRGB.get(0) < greenRUpper
        //        && rightRGB.get(1) > greenGLower && rightRGB.get(1) < greenGUpper
        //        && rightRGB.get(2) > greenBLower && rightRGB.get(2) < greenGUpper) {
        //    colors.add(PixelColor.GREEN);
        //} else if (rightRGB.get(0) > whiteRLower && rightRGB.get(0) < whiteRUpper
        //        && rightRGB.get(1) > whiteGLower && rightRGB.get(1) < whiteGUpper
        //        && rightRGB.get(2) > whiteBLower && rightRGB.get(2) < whiteBUpper) {
        //    colors.add(PixelColor.WHITE);
        //} else if (rightRGB.get(0) > purpleRLower && rightRGB.get(0) < purpleRUpper
        //        && rightRGB.get(1) > purpleGLower && rightRGB.get(1) < purpleGUpper
        //        && rightRGB.get(2) > purpleBLower && rightRGB.get(2) < purpleRUpper) {
        //    colors.add(PixelColor.PURPLE);
        //} else if (rightRGB.get(0) > yellowRLower && rightRGB.get(0) < yellowRUpper
        //        && rightRGB.get(1) > yellowGLower && rightRGB.get(1) < yellowGUpper
        //        && rightRGB.get(2) > yellowBLower && rightRGB.get(2) < yellowBUpper) {
        //    colors.add(PixelColor.YELLOW);
        //} else {
        //    colors.add(PixelColor.NONE);
        //}

        colors.add(PixelColor.NONE);

        bot.telem.addData("Colors", "Left: " + colors.get(0) + " Right: " + colors.get(1));
        return colors;
    }

    public Command init() {
        return new InstantCommand(() -> {
            intakeServo = bot.hMap.get(CRServo.class, "intakeServo");

            leftSensor = bot.hMap.get(ColorSensor.class, "leftColorSensor");
            //rightSensor = bot.hMap.get(ColorSensor.class, "rightColorSensor");

        });
    }
}
