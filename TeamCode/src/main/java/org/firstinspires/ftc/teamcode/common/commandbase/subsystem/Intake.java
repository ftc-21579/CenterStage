package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.common.centerstage.PixelColor;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.ArrayList;
import java.util.Arrays;

@Config
public class Intake extends SubsystemBase {

    Bot bot;

    private final CRServo leftServo, rightServo;
    private final ColorSensor leftSensor, rightSensor;

    private final ServoImplEx leftv4bServo, rightv4bServo;


    // TODO: we need to find a better way to do this
    public static int leftYellowRLower = 2000, leftYellowRUpper = 3000, leftYellowGLower = 3000, leftYellowGUpper = 5000, leftYellowBLower = 500, leftYellowBUpper = 1500;
    public static int leftPurpleRLower = 1000, leftPurpleRUpper = 3000, leftPurpleGLower = 3000, leftPurpleGUpper = 5000, leftPurpleBLower = 5000, leftPurpleBUpper = 7000;
    public static int leftGreenRLower = 500, leftGreenRUpper = 1000, leftGreenGLower = 2000, leftGreenGUpper = 3000, leftGreenBLower = 500, leftGreenBUpper = 1500;
    public static int leftWhiteRLower = 4000, leftWhiteRUpper = 6000, leftWhiteGLower = 8000, leftWhiteGUpper = 10000, leftWhiteBLower = 8000, leftWhiteBUpper = 10000;

    public static int rightYellowRLower = 1000, rightYellowRUpper = 2000, rightYellowGLower = 1000, rightYellowGUpper = 2000, rightYellowBLower = 250, rightYellowBUpper = 1000;
    public static int rightPurpleRLower = 500, rightPurpleRUpper = 2500, rightPurpleGLower = 1000, rightPurpleGUpper = 3000, rightPurpleBLower = 1000, rightPurpleBUpper = 3000;
    public static int rightGreenRLower = 250, rightGreenRUpper = 750, rightGreenGLower = 1000, rightGreenGUpper = 2000, rightGreenBLower = 250, rightGreenBUpper = 750;
    public static int rightWhiteRLower = 2500, rightWhiteRUpper = 4500, rightWhiteGLower = 3500, rightWhiteGUpper = 5500, rightWhiteBLower = 3000, rightWhiteBUpper = 5000;

    public Intake(Bot bot) {
        this.bot = bot;

        leftServo = bot.hMap.get(CRServo.class, "leftIntakeServo");
        rightServo = bot.hMap.get(CRServo.class, "rightIntakeServo");
        //leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        rightServo.setDirection(DcMotorSimple.Direction.REVERSE);

        leftSensor = bot.hMap.get(ColorSensor.class, "leftColorSensor");
        rightSensor = bot.hMap.get(ColorSensor.class, "rightColorSensor");

        leftv4bServo = bot.hMap.get(ServoImplEx.class, "intakeLeftV4BServo");
        leftv4bServo.setDirection(Servo.Direction.REVERSE);
        rightv4bServo = bot.hMap.get(ServoImplEx.class, "intakeRightV4BServo");
    }

    /**
     * Set the height of the V4B servos
     * @param height the height to set the servos to (0.0 - 1.0)
     */
    public void v4bCustomHeight(double height) {
        leftv4bServo.setPosition(height);
        rightv4bServo.setPosition(height);

        bot.telem.addData("Left Intake V4B Servo Position: ", leftv4bServo.getPosition());
        bot.telem.addData("Right Intake V4B Servo Position: ", rightv4bServo.getPosition());
    }

    /**
     * Set the power of the spinner
     * @param power the power to set the spinner to (-1.0 - 1.0)
     */
    public void setSpinnerPower(double power) {
        leftServo.setPower(power);
        rightServo.setPower(power);
    }

    /**
     * Get the power of the spinner
     * @return the power of the spinner (-1.0 - 1.0)
     */
    public double getSpinnerPower() {
        return (leftServo.getPower() + rightServo.getPower()) / 2;
    }

    /**
     * Get the position of the V4B servos
     * @return the position of the V4B servos (0.0 - 1.0)
     */
    public double getV4BPosition() {
        return (leftv4bServo.getPosition() + rightv4bServo.getPosition()) / 2;
    }

    /**
     * Get the color of the pixels from the color sensors in the intake hopper
     * @return the color of the pixels {left, right}
     */
    public ArrayList<PixelColor> getPixelColors() {
        ArrayList<Integer> leftRGB = new ArrayList<>(Arrays.asList(leftSensor.red(), leftSensor.green(), leftSensor.blue()));
        ArrayList<Integer> rightRGB = new ArrayList<>(Arrays.asList(rightSensor.red(), rightSensor.green(), rightSensor.blue()));

        ArrayList<PixelColor> colors = new ArrayList<>();

        if (leftRGB.get(0) > leftGreenRLower && leftRGB.get(0) < leftGreenRUpper
                && leftRGB.get(1) > leftGreenGLower && leftRGB.get(1) < leftGreenGUpper
                && leftRGB.get(2) > leftGreenBLower && leftRGB.get(2) < leftGreenBUpper) {
            colors.add(PixelColor.GREEN);
        } else if (leftRGB.get(0) > leftWhiteRLower && leftRGB.get(0) < leftWhiteRUpper
                && leftRGB.get(1) > leftWhiteGLower && leftRGB.get(1) < leftWhiteGUpper
                && leftRGB.get(2) > leftWhiteBLower && leftRGB.get(2) < leftWhiteBUpper) {
            colors.add(PixelColor.WHITE);
        } else if (leftRGB.get(0) > leftPurpleRLower && leftRGB.get(0) < leftPurpleRUpper
                && leftRGB.get(1) > leftPurpleGLower && leftRGB.get(1) < leftPurpleGUpper
                && leftRGB.get(2) > leftPurpleBLower && leftRGB.get(2) < leftPurpleBUpper) {
            colors.add(PixelColor.PURPLE);
        } else if (leftRGB.get(0) > leftYellowRLower && leftRGB.get(0) < leftYellowRUpper
                && leftRGB.get(1) > leftYellowGLower && leftRGB.get(1) < leftYellowGUpper
                && leftRGB.get(2) > leftYellowBLower && leftRGB.get(2) < leftYellowBUpper) {
            colors.add(PixelColor.YELLOW);
        } else {
            colors.add(PixelColor.NONE);
        }

        if (rightRGB.get(0) > rightGreenRLower && rightRGB.get(0) < rightGreenRUpper
                && rightRGB.get(1) > rightGreenGLower && rightRGB.get(1) < rightGreenGUpper
                && rightRGB.get(2) > rightGreenBLower && rightRGB.get(2) < rightGreenBUpper) {
            colors.add(PixelColor.GREEN);
        } else if (rightRGB.get(0) > rightWhiteRLower && rightRGB.get(0) < rightWhiteRUpper
                && rightRGB.get(1) > rightWhiteGLower && rightRGB.get(1) < rightWhiteGUpper
                && rightRGB.get(2) > rightWhiteBLower && rightRGB.get(2) < rightWhiteBUpper) {
            colors.add(PixelColor.WHITE);
        } else if (rightRGB.get(0) > rightPurpleRLower && rightRGB.get(0) < rightPurpleRUpper
                && rightRGB.get(1) > rightPurpleGLower && rightRGB.get(1) < rightPurpleGUpper
                && rightRGB.get(2) > rightPurpleBLower && rightRGB.get(2) < rightPurpleBUpper) {
            colors.add(PixelColor.PURPLE);
        } else if (rightRGB.get(0) > rightYellowRLower && rightRGB.get(0) < rightYellowRUpper
                && rightRGB.get(1) > rightYellowGLower && rightRGB.get(1) < rightYellowGUpper
                && rightRGB.get(2) > rightYellowBLower && rightRGB.get(2) < rightYellowBUpper) {
            colors.add(PixelColor.YELLOW);
        } else {
            colors.add(PixelColor.NONE);
        }

        bot.telem.addData("Colors", "Left: " + colors.get(0) + " Right: " + colors.get(1));
        /*
        bot.telem.addData("Left Red: ", leftSensor.red());
        bot.telem.addData("Left Green: ", leftSensor.green());
        bot.telem.addData("Left Blue: ", leftSensor.blue());
        bot.telem.addData("Right Red: ", rightSensor.red());
        bot.telem.addData("Right Green: ", rightSensor.green());
        bot.telem.addData("Right Blue: ", rightSensor.blue());
         */
        return colors;
    }
}
