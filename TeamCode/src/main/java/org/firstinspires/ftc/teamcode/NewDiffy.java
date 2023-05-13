package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.mineinjava.quail.swerveDrive;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.List;

@Config
@TeleOp(name="NewDiffy")
public class NewDiffy extends LinearOpMode {

    public static final double steeringGearRatio = 6.4;
    public static final double driveGearRatio = 1.5;
    private DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    public static double leftkp = 0.2, leftki = 0, leftkd = 0;
    public static double rightkp = 0.2, rightki = 0, rightkd = 0;

    private MiniPID leftPID = new MiniPID(leftkp, leftki, leftkd);
    private MiniPID rightPID = new MiniPID(rightkp, rightki, rightkd);

    private SwerveModule left, right;

    private final List<SwerveModule> modules = new ArrayList<>();

    @Override
    public void runOpMode() {
        // Initialize the motors
        leftUpperMotor = hardwareMap.get(DcMotor.class, "leftUpperMotor");
        leftLowerMotor = hardwareMap.get(DcMotor.class, "leftLowerMotor");
        rightLowerMotor = hardwareMap.get(DcMotor.class, "rightLowerMotor");
        rightUpperMotor = hardwareMap.get(DcMotor.class, "rightUpperMotor");

        leftLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Initialize the swerve modules
        left = new SwerveModule(new Vec2d(-0.454, 0), steeringGearRatio, driveGearRatio, leftPID, leftUpperMotor, leftLowerMotor, telemetry);
        right = new SwerveModule(new Vec2d(0.454, 0), steeringGearRatio, driveGearRatio, rightPID, rightUpperMotor, rightLowerMotor, telemetry);

        // Add the modules to the list of modules
        modules.add(left);
        modules.add(right);

        // Initialize the swerve drive class
        swerveDrive<SwerveModule> drive = new swerveDrive<>(modules);

        waitForStart();

        leftPID.reset();
        rightPID.reset();

        while (opModeIsActive()) {

            // Get the current position of the motors
            double leftUpperPosDeg = leftUpperMotor.getCurrentPosition() / 145.1 * 360;
            double leftLowerPosDeg = leftLowerMotor.getCurrentPosition() / 145.1 * 360;
            double rightUpperPosDeg = rightUpperMotor.getCurrentPosition() / 145.1 * 360;
            double rightLowerPosDeg = rightLowerMotor.getCurrentPosition() / 145.1 * 360;

            // Average the positions of the motors to get pod position
            double leftPodDeg = (leftUpperPosDeg + leftLowerPosDeg) / 2;
            double rightPodDeg = (rightUpperPosDeg + rightLowerPosDeg) / 2;

            // Calculate the odometry position of the modules
            double leftOdometryDeg = (leftPodDeg / steeringGearRatio) % 360;
            double rightOdometryDeg = (rightPodDeg / steeringGearRatio) % 360;

            telemetry.addData("Left Odo", leftOdometryDeg);
            telemetry.addData("Right Odo", rightOdometryDeg);

            // Get the joystick values
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rot = gamepad1.right_stick_x;

            // Calculate the vectors for each module
            Vec2d[] vectors = drive.calculateMoveAngles(new Vec2d(x, y), rot, 0);
            Vec2d[] normalizedVectors = drive.normalizeModuleVectors(vectors, 0.5);

            // Set the vectors for each module
            left.set(normalizedVectors[0], leftOdometryDeg);
            right.set(normalizedVectors[1], rightOdometryDeg);

            telemetry.update();
        }

    }
}
