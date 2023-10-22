package org.firstinspires.ftc.teamcode.common.drive.drive.swerve;

import static com.mineinjava.quail.util.util.deltaAngle;

import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

public class SwerveModule extends differentialSwerveModuleBase {
    public MiniPID pid;
    DcMotor upperMotor, lowerMotor;
    SuperTelemetry telemetry;
    String name;
    AbsoluteAnalogEncoder encoder;

    public SwerveModule(Vec2d position, double steeringGearRatio, double driveGearRatio, MiniPID pid, DcMotor upperMotor, DcMotor lowerMotor, AbsoluteAnalogEncoder encoder, SuperTelemetry telemetry, String name) {
        super(position, steeringGearRatio, driveGearRatio);
        this.pid = pid;
        this.upperMotor = upperMotor;
        this.lowerMotor = lowerMotor;
        this.encoder = encoder;
        this.telemetry = telemetry;
        this.name = name;
    }

    /**
     * Sets the module to a given vector
     * @param vector the vector to set the module to
     */
    public void set(Vec2d vector) {

        // If the vector length is 0, set the motor power to 0
        if (vector.getLength() == 0) {
            upperMotor.setPower(0);
            lowerMotor.setPower(0);

            return;
        }

        if (name.equals("Right")) {
            vector.scale(-1.0);
        }

        // The position of the pod in radians
        double odometryRad = encoder.getCurrentPosition();

        // The target angle is the angle of the vector
        double targetRad = vector.getAngle();

        double wheelFlipper = 1;
        double distanceToTarget = deltaAngle(odometryRad, targetRad);
        if (Math.abs(distanceToTarget) > Math.PI / 2) {
            targetRad += Math.PI;
            wheelFlipper = -1;
        }

        distanceToTarget = deltaAngle(odometryRad, targetRad);
        double setpointRad = odometryRad + distanceToTarget;

        // The rotation speed is the output of the PID controller
        double rotationSpeed = pid.getOutput(odometryRad, setpointRad);

        // Calculate the motor speeds
        double[] motorSpeeds = calculateMotorSpeeds(rotationSpeed, vector.getLength() * wheelFlipper);

        motorSpeeds[0] = motorSpeeds[0] * driveRatio;
        motorSpeeds[1] = motorSpeeds[1] * driveRatio;

        motorSpeeds[0] *= 2;
        motorSpeeds[1] *= 2;

        motorSpeeds = normalizeWheelSpeeds(motorSpeeds);

        // Set the motor powers
        upperMotor.setPower(motorSpeeds[0]);
        lowerMotor.setPower(motorSpeeds[1]);
    }

    private double[] normalizeWheelSpeeds(double[] speeds) {
        if (largestAbsolute(speeds) > 1) {
            double max = largestAbsolute(speeds);
            for (int i = 0; i < speeds.length; i++){
                speeds[i] /= max;
            }
        }
        return speeds;
    }

    private double largestAbsolute(double[] arr) {
        double largestAbsolute = 0;
        for (double d : arr) {
            double absoluteValue = Math.abs(d);
            if (absoluteValue > largestAbsolute) {
                largestAbsolute = absoluteValue;
            }
        }
        return largestAbsolute;
    }
}