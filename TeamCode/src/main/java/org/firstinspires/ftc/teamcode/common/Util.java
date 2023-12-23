package org.firstinspires.ftc.teamcode.common;

public class Util {

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}