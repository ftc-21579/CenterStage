package org.firstinspires.ftc.teamcode;

import androidx.core.math.MathUtils;

import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="Diffy")
public class Diffy extends LinearOpMode {

    // Differential swerve modules (vectors in feet, 1ft = 1 unit)
    private static final differentialSwerveModuleBase left = new differentialSwerveModuleBase(new Vec2d(-0.454, 0), 0.1563, 0.6250, false);
    private static final differentialSwerveModuleBase right = new differentialSwerveModuleBase(new Vec2d(0.454, 0), 0.1563, 0.6250, false);

    private final List<differentialSwerveModuleBase> modules = new ArrayList<>();

    private final ElapsedTime time = new ElapsedTime();

    private static DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    private final PIDController leftUpperPID = new PIDController(0.00002, 0, 0, time.seconds());
    private final PIDController leftLowerPID = new PIDController(0.00002, 0, 0, time.seconds());

    private final PIDController rightUpperPID = new PIDController(0.00002, 0, 0, time.seconds());
    private final PIDController rightLowerPID = new PIDController(0.00002, 0, 0, time.seconds());

    private final PIDController[] pidControllers = {leftUpperPID, leftLowerPID, rightUpperPID, rightLowerPID};
    private final List<DcMotor> motors = new ArrayList<>();

    private final double ticksPerDegree = 2.481;
    private final double ticksPerFullRotation = 893.16;

    @Override
    public void runOpMode() {

        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");

        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");

        motors.add(leftUpperMotor);
        motors.add(leftLowerMotor);
        motors.add(rightUpperMotor);
        motors.add(rightLowerMotor);

        modules.add(left);
        modules.add(right);

        swerveDrive<differentialSwerveModuleBase> drive = new swerveDrive<>(modules);

        waitForStart();

        while(opModeIsActive()) {

            double x = gamepad1.left_stick_x * 0.8;
            double y = gamepad1.left_stick_y * 0.8;
            double rot = gamepad1.right_stick_x * 0.8;

            telemetry.addLine(x + " " + y + " " + rot);

            Vec2d[] vectors = drive.calculateMoveAngles(new Vec2d(x, y), rot, 0, new Vec2d(0, 0));
            Vec2d[] normalizedVectors = drive.normalizeModuleVectors(vectors, 1.0);

            /*
            Vec2d[] normalizedVectors;
            if (gamepad1.a) {
                Vec2d[] vectors = {new Vec2d(1,0), new Vec2d(1,0)};
                normalizedVectors = drive.normalizeModuleVectors(vectors, 1.0);
            }
            else if (gamepad1.b) {
                Vec2d[] vectors = {new Vec2d(0,1), new Vec2d(0,1)};
                normalizedVectors = drive.normalizeModuleVectors(vectors, 1.0);
            }
            else {
                Vec2d[] vectors = {new Vec2d(0, 0), new Vec2d(0, 0)};
                normalizedVectors = drive.normalizeModuleVectors(vectors, 1.0);
            }
             */

            double[][] powers = {{0, 0}, {0, 0}};

            double leftOdometry = ((leftUpperMotor.getCurrentPosition() - leftLowerMotor.getCurrentPosition()) * 0.1563) % 360;
            double rightOdometry = ((rightUpperMotor.getCurrentPosition() - rightLowerMotor.getCurrentPosition()) * 0.1563) % 360;

            double[] odometry = {leftOdometry, rightOdometry};

            for (int i = 0; i < normalizedVectors.length; i++) {
                DcMotor motor = motors.get(i);
                int target = (int) ((int) (normalizedVectors[i].getAngle() * ticksPerDegree) % ticksPerFullRotation);

                double rotationSpeed = pidControllers[i].update(odometry[i], normalizedVectors[i].getAngle(), time.seconds());
                double wheelSpeed = normalizedVectors[i].getLength();

                powers[i] = modules.get(i).calculateMotorSpeeds(rotationSpeed, wheelSpeed);
            }

            // Left module motors
            leftUpperMotor.setPower(Math.max(-1, Math.min(1, powers[0][0])));
            leftLowerMotor.setPower(Math.max(-1, Math.min(1, powers[0][1])));

            // Right module motors
            rightUpperMotor.setPower(Math.max(-1, Math.min(1, powers[1][0])));
            rightLowerMotor.setPower(Math.max(-1, Math.min(1, powers[1][1])));

            telemetry.addData("Powers", powers[0][0] + " " + powers[0][1] + " " + powers[1][0] + " " + powers[1][1]);
            telemetry.addData("Vectors", normalizedVectors[0] + " " + normalizedVectors[1]);

            telemetry.update();
        }
    }
}