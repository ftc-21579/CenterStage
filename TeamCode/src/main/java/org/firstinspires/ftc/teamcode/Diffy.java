package org.firstinspires.ftc.teamcode;

import com.mineinjava.quail.differentialSwerveModuleBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.List;

public class Diffy extends LinearOpMode {

    private static final differentialSwerveModuleBase left = new differentialSwerveModuleBase(new Vec2d(-1, 0), 2, 3, false);
    private static final differentialSwerveModuleBase right = new differentialSwerveModuleBase(new Vec2d(1, 0), 2, 3, false);

    private static List<differentialSwerveModuleBase> modules = new ArrayList<>();

    private static DcMotor leftUpperMotor, rightUpperMotor;

    @Override
    public void runOpMode() {

        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");

        swerveDrive<differentialSwerveModuleBase> drive = new swerveDrive<>(modules);

        waitForStart();

        while(opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rot = gamepad1.right_stick_x;

            Vec2d[] vectors = drive.calculateMoveAngles(new Vec2d(x, y), rot, 0, new Vec2d(0, 0));

            double[] powers = {};

            for (int i = 0; i < vectors.length; i++) {
                powers[i] = modules.get(i).calculateMotorSpeeds(vectors[i]);
            }

            // Left module motors
            leftUpperMotor.setPower(powers[0][0]);
            leftLowerMotor.setPower(powers[0][1]);

            // Right module motors
            rightUpperMotor.setPower(powers[1][0]);
            rightLowerMotor.setPower(powers[1][1]);


        }

    }

}








