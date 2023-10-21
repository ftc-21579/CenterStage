package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.InstantCommand;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;

@Config
public class Intake {

    Bot bot;

    private CRServo intakeServo;
    private NormalizedColorSensor leftSensor, rightSensor;

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

    public ArrayList<PixelColor> getPixelColors() {
        NormalizedRGBA leftReading = leftSensor.getNormalizedColors();
        //int rightReading = rightSensor.argb();

        bot.telem.addData("Left Reading", leftReading.toString());
        //bot.telem.addData("Right Reading", rightReading);

        ArrayList<PixelColor> colors = new ArrayList<>();

        // temp code
        colors.add(PixelColor.NONE);
        colors.add(PixelColor.NONE);

        return colors;
    }

    public Command init() {
        return new InstantCommand(() -> {
            intakeServo = bot.hMap.get(CRServo.class, "intakeServo");

            leftSensor = bot.hMap.get(NormalizedColorSensor.class, "leftColorSensor");
            //rightSensor = bot.hMap.get(ColorSensor.class, "rightColorSensor");

            ((SwitchableLight)leftSensor).enableLight(true);
            //rightSensor.enableLed(true);
        });
    }
}
