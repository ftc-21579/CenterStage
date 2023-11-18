package org.firstinspires.ftc.teamcode.opmode.auto;

import com.mineinjava.quail.pathing.Path;
import com.mineinjava.quail.util.geometry.Pose2d;

import java.util.ArrayList;
import java.util.Arrays;

public class RedConstants {

    public static final double START_X = 51.3;
    public static final double LEFT_START_Y = 12.0;
    public static final double RIGHT_START_Y = -36.0;

    public static final double START_HEADING = 1.5 * Math.PI;


    public Path LeftPurplePixelPath(double start_y) {
        return new Path(new ArrayList<Pose2d>(
                Arrays.asList(
                        new Pose2d(START_X, start_y, START_HEADING),
                        new Pose2d(START_X - 17, start_y, 1.25 * Math.PI),
                        new Pose2d(30, start_y - 4, Math.PI)
                )
        ));
    }

    public Path CenterPurplePixelPath(double start_y) {
        return new Path(new ArrayList<Pose2d>(
                Arrays.asList(
                        new Pose2d(START_X, start_y, START_HEADING),
                        new Pose2d(32, start_y, START_HEADING)
                )
        ));
    }

    public Path RightPurplePixelPath(double start_y) {
        return new Path(new ArrayList<Pose2d>(
                Arrays.asList(
                        new Pose2d(START_X, start_y, START_HEADING),
                        new Pose2d(START_X - 17, start_y, 0.75 * Math.PI),
                        new Pose2d(30, start_y + 4, 0)
                )
        ));
    }

}
