package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity botOne = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(66, 66, Math.toRadians(180), Math.toRadians(180), 12)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 64, Math.toRadians(90)))
                                .setReversed(true)
                                .splineTo(new Vector2d(7, 32), Math.toRadians(-135))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .setTangent(0)
                                .splineToSplineHeading(new Pose2d(48, 40, Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(1)
                                .setReversed(false)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(180))
                                .splineTo(new Vector2d(-50, 12), Math.toRadians(180))
                                .waitSeconds(2)
                                .setReversed(true)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(0))
                                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .build());

        RoadRunnerBotEntity botTwo = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(66, 66, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 64, Math.toRadians(90)))
                                .setReversed(true)
                                .lineTo(new Vector2d(12, 32))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .setTangent(0)
                                .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(180))
                                .splineTo(new Vector2d(-50, 12), Math.toRadians(180))
                                .waitSeconds(1)
                                .setReversed(true)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(0))
                                .splineTo(new Vector2d(48, 32), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .build());

        RoadRunnerBotEntity botThree = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedLight())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(66, 66, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(16, 17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 64, Math.toRadians(90)))
                                .setReversed(true)
                                .lineTo(new Vector2d(12, 32))
                                .waitSeconds(0.5)
                                .setReversed(false)
                                .setTangent(0)
                                .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(180))
                                .splineTo(new Vector2d(-50, 12), Math.toRadians(180))
                                .waitSeconds(1)
                                .setReversed(true)
                                .splineTo(new Vector2d(12, 12), Math.toRadians(0))
                                .splineTo(new Vector2d(48, 32), Math.toRadians(0))
                                .waitSeconds(0.5)
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(botOne)
                .addEntity(botTwo)
                .start();
    }
}