package org.firstinspires.ftc.teamcode;

import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@TeleOp(name="Diffy")
@Config
public class Diffy extends LinearOpMode {

    // Differential swerve modules (vectors in feet, 1ft = 1 unit)
    private static final differentialSwerveModuleBase left = new differentialSwerveModuleBase(new Vec2d(-0.454, 0), 0.1563, 0.6250, false);
    private static final differentialSwerveModuleBase right = new differentialSwerveModuleBase(new Vec2d(0.454, 0), 0.1563, 0.6250, false);

    private final List<differentialSwerveModuleBase> modules = new ArrayList<>();

    private ElapsedTime time = new ElapsedTime();

    private static DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    public static double kp = 0.2;
    public static double ki = 0;
    public static double kd = 0;

    private final PIDController leftUpperPID = new PIDController(kp, ki, kd, time.seconds());
    private final PIDController leftLowerPID = new PIDController(kp, ki, kd, time.seconds());

    private final PIDController rightUpperPID = new PIDController(kp, ki, kd, time.seconds());
    private final PIDController rightLowerPID = new PIDController(kp, ki, kd, time.seconds());

    private final PIDController[] pidControllers = {leftUpperPID, leftLowerPID, rightUpperPID, rightLowerPID};
    private final List<DcMotor> motors = new ArrayList<>();

    private final double ticksPerDegree = 2.481;
    private final double ticksPerFullRotation = 893.16;

    DecimalFormat df = new DecimalFormat("##.0000");

    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    @Override
    public void runOpMode() {

        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");

        leftLowerMotor.setDirection(DcMotor.Direction.REVERSE);

        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");

        rightLowerMotor.setDirection(DcMotor.Direction.REVERSE);

        leftLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motors.add(leftUpperMotor);
        motors.add(leftLowerMotor);
        motors.add(rightUpperMotor);
        motors.add(rightLowerMotor);

        modules.add(left);
        modules.add(right);

        swerveDrive<differentialSwerveModuleBase> drive = new swerveDrive<>(modules);

        waitForStart();

        time.reset();

        while(opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();

            double x = gamepad1.left_stick_x * 0.8;
            double y = gamepad1.left_stick_y * 0.8;
            double rot = gamepad1.right_stick_x * 0.8;

            telemetry.addLine("Gamepad: " + x + " | " + y + " | " + rot);


            //Vec2d[] vectors = drive.calculateMoveAngles(new Vec2d(x, y), rot, 0, new Vec2d(0, 0));
            //Vec2d[] normalizedVectors = drive.normalizeModuleVectors(vectors, 0.25);


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



            double[][] powers = {{0, 0}, {0, 0}};
            // 11.83

            double leftUpperPos = leftUpperMotor.getCurrentPosition() / 145.1 * 360;
            double leftLowerPos = leftLowerMotor.getCurrentPosition() / 145.1 * 360;
            double rightUpperPos = rightUpperMotor.getCurrentPosition() / 145.1 * 360;
            double rightLowerPos = rightLowerMotor.getCurrentPosition() / 145.1 * 360;

            double leftPod = (leftUpperPos + leftLowerPos) / 2;
            double rightPod = (rightUpperPos + rightLowerPos) / 2;

            double leftOdometry = (leftPod / 6) % 360;
            double rightOdometry = (rightPod / 6) % 360;

            telemetry.addData("leftOdo", df.format(leftOdometry));
            telemetry.addData("rightOdo", df.format(rightOdometry));
            packet.put("leftOdo", df.format(leftOdometry));
            packet.put("rightOdo", df.format(rightOdometry));

            packet.put("leftUpperMotor", leftUpperMotor.getCurrentPosition());
            packet.put("leftLowerMotor", leftLowerMotor.getCurrentPosition());
            packet.put("rightUpperMotor", rightUpperMotor.getCurrentPosition());
            packet.put("rightLowerMotor", rightLowerMotor.getCurrentPosition());

            double[] odometry = {leftOdometry, rightOdometry};

            for (int i = 0; i < normalizedVectors.length; i++) {
                DcMotor motor = motors.get(i);
                //int target = (int) ((int) (normalizedVectors[i].getAngle() * ticksPerDegree) % ticksPerFullRotation);

                double rotationSpeed = pidControllers[i].update(odometry[i], normalizedVectors[i].getAngle(), time.seconds());
                double wheelSpeed = normalizedVectors[i].getLength();

                telemetry.addData(i + "", df.format(normalizedVectors[i].getAngle()) + " | " + df.format(rotationSpeed) + " | " + df.format(wheelSpeed));
                packet.put(i + " ", df.format(normalizedVectors[i].getAngle()) + " | " + df.format(rotationSpeed) + " | " + df.format(wheelSpeed) + " | " + pidControllers[i].ERROR);

                powers[i] = modules.get(i).calculateMotorSpeeds(wheelSpeed, rotationSpeed);
            }

            //powers[0][0] = Math.max(-1, Math.min(1, powers[0][0]));
            //powers[0][1] = Math.max(-1, Math.min(1, powers[0][1]));
            //powers[1][0] = Math.max(-1, Math.min(1, powers[1][0]));
            //powers[1][1] = Math.max(-1, Math.min(1, powers[1][1]));

            powers[0] = normalizeWheelSpeeds(powers[0]);
            powers[1] = normalizeWheelSpeeds(powers[1]);

            // Left module motors
            leftUpperMotor.setPower(powers[0][0] * 0.5);
            leftLowerMotor.setPower(powers[0][1] * 0.5);

            // Right module motors
            rightUpperMotor.setPower(powers[1][0] * 0.5);
            rightLowerMotor.setPower(powers[1][1] * 0.5);

            String[][] powersString = new String[2][2];

            powersString[0][0] = df.format(powers[0][0]);
            powersString[0][1] = df.format(powers[0][1]);
            powersString[1][0] = df.format(powers[1][0]);
            powersString[1][1] = df.format(powers[1][1]);

            telemetry.addData("Powers", powersString[0][0] + " " + powersString[0][1] + " " + powersString[1][0] + " " + powersString[1][1]);
            telemetry.addData("Vectors", normalizedVectors[0] + " " + normalizedVectors[1]);

            packet.put("Powers", powersString[0][0] + " | " + powersString[0][1] + " | " + powersString[1][0] + " | " + powersString[1][1]);
            packet.put("Vectors", normalizedVectors[0] + " " + normalizedVectors[1]);

            telemetry.update();
            dashboard.sendTelemetryPacket(packet);
        }
    }

    public double[] normalizeWheelSpeeds(double[] speeds) {
        if (largest(speeds) > 1) {
            double max = largest(speeds);
            for (int i = 0; i < speeds.length; i++){
                speeds[i] /= max;
            }
        }
        return speeds;
    }

    private double largest(double[] arr) {
        double largest = 0;
        for (double d : arr) {
            if (d > largest) {
                largest = d;
            }
        }
        return largest;
    }
}