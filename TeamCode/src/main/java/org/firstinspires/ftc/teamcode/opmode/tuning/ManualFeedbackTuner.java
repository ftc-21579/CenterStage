package org.firstinspires.ftc.teamcode.opmode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.drive.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.TwoWheelLocalizer;

public final class ManualFeedbackTuner extends LinearOpMode {
    public static double DISTANCE = 64;

    @Override
    public void runOpMode() throws InterruptedException {
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
            
            if (drive.localizer instanceof TwoWheelLocalizer) {
                if (TwoWheelLocalizer.PARAMS.perpXTicks == 0 && TwoWheelLocalizer.PARAMS.parYTicks == 0) {
                    throw new AssertionError("Odometry wheel locations not set! Run AngularRampLogger to tune them.");
                }
            }
            waitForStart();

            while (opModeIsActive()) {
                Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(DISTANCE)
                            .lineToX(0)
                            .build());
            }
        } else {
            throw new AssertionError();
        }
    }
}
