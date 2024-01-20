package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static double blueLeftX = 12, blueLeftY = 64;
    public static double blueRightX = -36, blueRightY = 64;
    public static double redLeftX = -36, redLeftY = -64;
    public static double redRightX = 12, redRightY = -64;
    public static double backdropX = 49;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity blueLeftRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(blueLeftX, blueLeftY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineTo(new Vector2d(blueLeftX, 60))
                                .turn(Math.toRadians(-90))
                                .lineToSplineHeading(new Pose2d(blueLeftX + 5, 32, Math.toRadians(0)))
                                //.splineTo(new Vector2d(10, 46), Math.toRadians(225))
                                //.lineToSplineHeading(new Pose2d(10, 46, Math.toRadians(45)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, 30, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, 30))
                                .build());

        RoadRunnerBotEntity blueLeftCenter = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(blueLeftX, blueLeftY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(26, 36, Math.toRadians(45)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, 36, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, 36))
                                .build());

        RoadRunnerBotEntity blueLeftLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(blueLeftX, blueLeftY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(30, 48, Math.toRadians(45)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, 42, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, 42))
                                .build());

        RoadRunnerBotEntity blueRightRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(blueRightX, blueRightY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineTo(new Vector2d(-36, 60))
                                .turn(Math.toRadians(-90))
                                .lineToSplineHeading(new Pose2d(-36, 32, Math.toRadians(0)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(-36, 12))
                                .turn(Math.toRadians(180))
                                .lineTo(new Vector2d(24, 12))
                                .splineToConstantHeading(new Vector2d(backdropX, 30), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, 30))
                                .build());

        RoadRunnerBotEntity blueRightCenter = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(blueRightX, blueRightY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36, 12, Math.toRadians(270)))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(-90))
                                .lineTo(new Vector2d(24, 12))
                                .splineToConstantHeading(new Vector2d(backdropX, 36), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, 36))
                                .build());

        RoadRunnerBotEntity blueRightLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(blueRightX, blueRightY, Math.toRadians(90)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36, 32, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(-36, 12))
                                .lineTo(new Vector2d(24, 12))
                                .splineToConstantHeading(new Vector2d(backdropX, 42), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, 42))
                                .build());

        RoadRunnerBotEntity redLeftRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redLeftX, redLeftY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineTo(new Vector2d(redLeftX, redLeftY + 4))
                                .turn(Math.toRadians(-90))
                                .lineToSplineHeading(new Pose2d(redLeftX - 5, -32, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(redLeftX - 5, -14))
                                .lineTo(new Vector2d(24, -14))
                                .splineToConstantHeading(new Vector2d(backdropX, -42), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -42))
                                .build());

        RoadRunnerBotEntity redLeftCenter = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redLeftX, redLeftY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-36, -12, Math.toRadians(91)))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(89))
                                .lineTo(new Vector2d(24, -12))
                                .splineToConstantHeading(new Vector2d(backdropX, -36), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -36))
                                .build());

        RoadRunnerBotEntity redLeftLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redLeftX, redLeftY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineTo(new Vector2d(redLeftX, -60))
                                .turn(Math.toRadians(90))
                                .lineToSplineHeading(new Pose2d(redLeftX + 2, -36, Math.toRadians(0)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(-36, -12))
                                .turn(Math.toRadians(180))
                                .lineTo(new Vector2d(24, -12))
                                .splineToConstantHeading(new Vector2d(backdropX, -30), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -30))
                                .build());

        RoadRunnerBotEntity redRightRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redRightX, redRightY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(30, -48, Math.toRadians(315)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, -42, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -42))
                                .build());

        RoadRunnerBotEntity redRightCenter = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redRightX, redRightY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(26, -36, Math.toRadians(315)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, -36, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -36))
                                .build());

        RoadRunnerBotEntity redRightLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redRightX, redRightY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(10, -46, Math.toRadians(315)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, -30, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -30))
                                .build());

        RoadRunnerBotEntity test = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(50, 50, Math.toRadians(142), Math.toRadians(142), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(redRightX, redRightY, Math.toRadians(270)))
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(10, -46, Math.toRadians(315)))
                                .waitSeconds(0.5)
                                .lineToSplineHeading(new Pose2d(backdropX, -30, Math.toRadians(180)))
                                .waitSeconds(0.5)
                                .waitSeconds(0.5)
                                .lineTo(new Vector2d(backdropX - 6, -30))
                                .waitSeconds(0.5)
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(blueLeftRight)
                //.addEntity(blueLeftCenter)
                //.addEntity(blueLeftLeft)
                //.addEntity(blueRightRight)
                //.addEntity(blueRightCenter)
                //.addEntity(blueRightLeft)
                //.addEntity(redLeftRight)
                //.addEntity(redLeftCenter)
                .addEntity(redLeftLeft)
                //.addEntity(redRightRight)
                //.addEntity(redRightCenter)
                //.addEntity(redRightLeft)
                //.addEntity(test)
                .start();
    }
}