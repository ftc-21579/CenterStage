package org.firstinspires.ftc.teamcode;

import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="Diffy")
public class Diffy extends LinearOpMode {

    private static final differentialSwerveModuleBase left = new differentialSwerveModuleBase(new Vec2d(-0.454, 0), 0.1563, 0.6250, false);
    private static final differentialSwerveModuleBase right = new differentialSwerveModuleBase(new Vec2d(0.454, 0), 0.1563, 0.6250, false);

    private final static List<differentialSwerveModuleBase> modules = new ArrayList<>();

    private final ElapsedTime time = new ElapsedTime();

    private static DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    private final PIDController leftUpperPID = new PIDController(0.1, 0, 0, time.seconds());
    private final PIDController leftLowerPID = new PIDController(0.1, 0, 0, time.seconds());

    private final PIDController rightUpperPID = new PIDController(0.1, 0, 0, time.seconds());
    private final PIDController rightLowerPID = new PIDController(0.1, 0, 0, time.seconds());

    private final PIDController[] pidControllers = {leftUpperPID, leftLowerPID, rightUpperPID, rightLowerPID};
    private final DcMotor[] motors = {leftUpperMotor, leftLowerMotor, rightUpperMotor, rightLowerMotor};

    private final double ticksPerDegree = 2.481;

    @Override
    public void runOpMode() {

        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");

        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");

        modules.add(left);
        modules.add(right);

        swerveDrive<differentialSwerveModuleBase> drive = new swerveDrive<>(modules);

        waitForStart();

        while(opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rot = gamepad1.right_stick_x;

            Vec2d[] vectors = drive.calculateMoveAngles(new Vec2d(x, y), rot, 0, new Vec2d(0, 0));

            double[][] powers = {};

            for (int i = 0; i < vectors.length; i++) {
                int current = motors[i].getCurrentPosition();
                int target = (int) (vectors[i].getAngle() * ticksPerDegree);
                double power = pidControllers[i].update(current, target, time.seconds());
                double length = vectors[i].getLength();
                powers[i] = modules.get(i).calculateMotorSpeeds(length, power);
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








