package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static double leftX = 12, leftY = 64;
    public static double rightX = -36, rightY = 64;
    public static double backdropX = 49;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity blueLeftRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(10, 46, Math.toRadians(45)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, 30, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(46, 30))
                                .build());

        RoadRunnerBotEntity blueLeftCenter = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(26, 36, Math.toRadians(45)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, 36, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(46, 36))
                                .build());

        RoadRunnerBotEntity blueLeftLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(leftX, leftY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(30, 48, Math.toRadians(45)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, 42, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(46, 42))
                                .build());

        RoadRunnerBotEntity blueRightRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36, 64, Math.toRadians(90)))
                                .setReversed(true)
                                .splineTo(new Vector2d(-36, 40), Math.toRadians(-135))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .splineToSplineHeading(new Pose2d(-24, 36, Math.toRadians(180)), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(50, 30, Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(0.5)
                                //.splineTo(new Vector2d(12, 12), Math.toRadians(180))
                                //.splineTo(new Vector2d(-50, 12), Math.toRadians(180))
                                //.waitSeconds(1)
                                //.setReversed(true)
                                //.splineTo(new Vector2d(12, 12), Math.toRadians(0))
                                //.splineTo(new Vector2d(48, 32), Math.toRadians(0))
                                //.waitSeconds(0.5)
                                .build());

        RoadRunnerBotEntity blueRightCenter = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-36, 63, Math.toRadians(90)))
                                .setReversed(true)
                                .lineTo(new Vector2d(-36, 40))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .splineToSplineHeading(new Pose2d(-24, 36, Math.toRadians(180)), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(50, 36, Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(0.5)
                                //.setReversed(true)
                                //.splineTo(new Vector2d(12, 12), Math.toRadians(180))
                                //.splineTo(new Vector2d(-50, 12), Math.toRadians(180))
                                //.waitSeconds(1)
                                //.setReversed(false)
                                //.splineTo(new Vector2d(12, 12), Math.toRadians(0))
                                //.splineTo(new Vector2d(48, 32), Math.toRadians(0))
                                //.waitSeconds(0.5)
                                .build());

        RoadRunnerBotEntity blueRightLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(rightX, rightY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36, 32, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                //.lineToSplineHeading(new Pose2d(backdropX, 42, Math.toRadians(180)))
                                //.waitSeconds(0.5)
                                //.waitSeconds(0.5)
                                //.lineTo(new Vector2d(46, 42))
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(blueLeftRight)
                //.addEntity(blueLeftCenter)
                //.addEntity(blueLeftLeft)
                //.addEntity(blueRightRight)
                //.addEntity(blueRightCenter)
                .addEntity(blueRightLeft)
                .start();
    }
}