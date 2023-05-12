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
    
    public static final double steeringGearRatio = 6.4;
    public static final double driveGearRatio = 1.5;

    // Differential swerve modules (vectors in feet, 1ft = 1 unit)
    private static final differentialSwerveModuleBase left = new differentialSwerveModuleBase(new Vec2d(-0.454, 0), steeringGearRatio, driveGearRatio, false);
    private static final differentialSwerveModuleBase right = new differentialSwerveModuleBase(new Vec2d(0.454, 0), steeringGearRatio, driveGearRatio, false);

    private final List<differentialSwerveModuleBase> modules = new ArrayList<>();

    private ElapsedTime time = new ElapsedTime();

    private static DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    public static double leftkp = 0.2;
    public static double leftki = 0;
    public static double leftkd = 0;

    public static double rightkp = 0.2;
    public static double rightki = 0;
    public static double rightkd = 0;

    private final PIDController leftPID = new PIDController(leftkp, leftki, leftkd, time.seconds());
    private final PIDController rightPID = new PIDController(rightkp, rightki, rightkd, time.seconds());

    private final PIDController[] pidControllers = {leftPID, rightPID};
    private final List<DcMotor> motors = new ArrayList<>();

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

            double leftOdometryDeg = (leftPod / steeringGearRatio) % 360;
            double rightOdometryDeg = (rightPod / steeringGearRatio) % 360;

            telemetry.addData("leftOdo", df.format(leftOdometryDeg));
            telemetry.addData("rightOdo", df.format(rightOdometryDeg));
            packet.put("leftOdo", df.format(leftOdometryDeg));
            packet.put("rightOdo", df.format(rightOdometryDeg));

            packet.put("leftUpperMotor", leftUpperPos);
            packet.put("leftLowerMotor", leftLowerPos);
            packet.put("rightUpperMotor", rightUpperPos);
            packet.put("rightLowerMotor", rightLowerPos);

            double[] odometry = {leftOdometryDeg, rightOdometryDeg};

            for (int i = 0; i < normalizedVectors.length; i++) {
                //DcMotor motor = motors.get(i);
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