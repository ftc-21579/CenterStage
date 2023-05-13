package org.firstinspires.ftc.teamcode;

import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@Disabled
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
    private boolean isZeroMovement = true;

    public static double leftkp = 0.2;
    public static double leftki = 0;
    public static double leftkd = 0;

    public static double rightkp = 0.2;
    public static double rightki = 0;
    public static double rightkd = 0;

    //private final PIDController leftPID = new PIDController(leftkp, leftki, leftkd, time.seconds());
    //private final PIDController rightPID = new PIDController(rightkp, rightki, rightkd, time.seconds());

    private final MiniPID leftPID = new MiniPID(leftkp, leftki, leftkd);
    private final MiniPID rightPID = new MiniPID(rightkp, rightki, rightkd);

    //private final PIDController[] pidControllers = {leftPID, rightPID};
    private final MiniPID[] pidControllers = {leftPID, rightPID};
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
        leftPID.reset();
        rightPID.reset();

        while(opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rot = gamepad1.right_stick_x;

            if (x == 0 && y == 0 && rot == 0) {
                isZeroMovement = true;
            }
            else {
                isZeroMovement = false;
            }

            telemetry.addLine("Gamepad: " + x + " | " + y + " | " + rot);

            Vec2d[] normalizedVectors = {new Vec2d(0, 0), new Vec2d(0, 0)};

            if (!isZeroMovement) {
                Vec2d[] vectors = drive.calculateMoveAngles(new Vec2d(x, y), rot, 0, new Vec2d(0, 0));
                normalizedVectors = drive.normalizeModuleVectors(vectors, 0.5);
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

            if (!isZeroMovement) {
                for (int i = 0; i < normalizedVectors.length; i++) {

                    // Speed at which the pod should rotate
                    double rotationSpeed = 0.0;

                    // If there is no movement, don't try to get the angle......
                    double target = Math.toDegrees(normalizedVectors[i].getAngle());

                    double current = odometry[i];

                    MiniPID pid = pidControllers[i];

                    pid.setSetpoint(target);
                    rotationSpeed = pid.getOutput(current);

                    rotationSpeed = Math.min(-1, Math.max(1, rotationSpeed));

                    double wheelSpeed = 0.0;

                    if (x != 0 && y != 0) {
                        wheelSpeed = normalizedVectors[i].getLength();
                    }

                    telemetry.addData(i + "", df.format(normalizedVectors[i].getAngle()) + " | " + df.format(rotationSpeed) + " | " + df.format(wheelSpeed));
                    packet.put(i + " ", df.format(normalizedVectors[i].getAngle()) + " | " + df.format(rotationSpeed) + " | " + df.format(wheelSpeed) + " | ");

                    powers[i] = modules.get(i).calculateMotorSpeeds(rotationSpeed, wheelSpeed);
                }
                        }
            else {
                for (int i = 0; i < normalizedVectors.length; i++) {
                    powers[i] = modules.get(i).calculateMotorSpeeds(0, 0);
                }
            }

            powers[0] = normalizeWheelSpeeds(powers[0]);
            powers[1] = normalizeWheelSpeeds(powers[1]);

            // Left module motors
            leftUpperMotor.setPower(powers[0][0]);
            leftLowerMotor.setPower(powers[0][1]);

            // Right module motors
            rightUpperMotor.setPower(powers[1][0]);
            rightLowerMotor.setPower(powers[1][1]);

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