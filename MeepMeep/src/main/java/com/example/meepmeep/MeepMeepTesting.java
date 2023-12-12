package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(66, 66, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(16, 17)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, 64, Math.toRadians(270)))
                .splineTo(new Vector2d(7, 32), Math.toRadians(-135))
                .waitSeconds(0.5)
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(48, 36, Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(0.5)
                .setReversed(true)
                .splineTo(new Vector2d(12, 12), Math.toRadians(180))
                .splineTo(new Vector2d(-60, 12), Math.toRadians(180))
                .waitSeconds(1)
                .setReversed(false)
                .splineTo(new Vector2d(12, 12), Math.toRadians(0))
                .splineTo(new Vector2d(48, 36), Math.toRadians(0))
                .waitSeconds(0.5)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}