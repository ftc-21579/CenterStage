package org.firstinspires.ftc.teamcode.common;

import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.control.PIDCoefficients;
import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.geometry.Vector2d;
import com.amarcolini.joos.hardware.Imu;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.MotorGroup;

import org.firstinspires.ftc.teamcode.common.drive.DifferentialSwerveDrive;
import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

public class DiffyBot extends Robot {

    public static boolean isFieldCentric = true;

    public static final double steeringGearRatio = 4;
    public static final double driveGearRatio = 1;
    public static PIDCoefficients moduleOrientationCoefficients = new PIDCoefficients(6, 0, 0);
    public static PIDCoefficients translationCoefficients = new PIDCoefficients(1, 0, 0);
    public static PIDCoefficients headingCoefficients = new PIDCoefficients(1, 0, 0);

    public final DifferentialSwerveDrive drive;
    Imu imu;

    private final SuperTelemetry telem;

    public DiffyBot(SuperTelemetry telem) {
        this.telem = telem;

        drive = new DifferentialSwerveDrive(
                new MotorGroup(
                        new Motor(hMap, "leftUpperMotor", Motor.Kind.GOBILDA_1150),
                        new Motor(hMap,"leftLowerMotor", Motor.Kind.GOBILDA_1150).reversed()
                ),
                new MotorGroup(
                        new Motor(hMap, "rightUpperMotor", Motor.Kind.GOBILDA_1150),
                        new Motor(hMap, "rightLowerMotor", Motor.Kind.GOBILDA_1150).reversed()
                ),
                new AbsoluteAnalogEncoder(hMap.analogInput.get("leftEncoder")),
                new AbsoluteAnalogEncoder(hMap.analogInput.get("rightEncoder")),
                steeringGearRatio,
                driveGearRatio,
                isFieldCentric,
                imu,
                moduleOrientationCoefficients,
                translationCoefficients,
                headingCoefficients,
                telem
        );

        register(drive);
    }

    @Override
    public void init() {
        Vector2d leftStick = gamepad().p1.getLeftStick();
        double x = leftStick.x;
        double y = leftStick.y;
        double rot = gamepad().p1.getRightStick().x;

        if (isInTeleOp) {

            drive.setDrivePower(new Pose2d(x, y, rot));
        }

    }

}
