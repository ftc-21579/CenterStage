package org.firstinspires.ftc.teamcode;

import static com.mineinjava.quail.util.util.deltaAngle;

import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SwerveModule extends differentialSwerveModuleBase {
    public MiniPID pid;
    DcMotor upperMotor, lowerMotor;
    Telemetry telemetry;
    String name;

    public SwerveModule(Vec2d position, double steeringGearRatio, double driveGearRatio, MiniPID pid, DcMotor upperMotor, DcMotor lowerMotor, Telemetry telemetry, String name) {
        super(position, steeringGearRatio, driveGearRatio);
        this.pid = pid;
        this.upperMotor = upperMotor;
        this.lowerMotor = lowerMotor;
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

            telemetry.addLine(name + " Vector length is 0, setting motor power to 0");

            return;
        }

        // Get the current motor angles in radians
        double upperPosRad = upperMotor.getCurrentPosition() / 145.1 * (Math.PI * 2);
        double lowerPosRad = lowerMotor.getCurrentPosition() / 145.1 * (Math.PI * 2);

        // The odometry angle is the average of the upper and lower pod angles
        double podRad = (upperPosRad + lowerPosRad) / 2;

        // The odometry angle is the angle of the pod divided by the steering ratio
        double odometryRad = (podRad / super.steeringRatio);
        telemetry.addData(name + " Odometry angle", Math.toDegrees(odometryRad));

        // The target angle is the angle of the vector
        double targetRad = vector.getAngle();
        telemetry.addData(name + " Target angle", Math.toDegrees(targetRad));

        double distanceToTarget = deltaAngle(odometryRad, targetRad);
        double setpointRad = odometryRad + distanceToTarget;

        // The rotation speed is the output of the PID controller
        double rotationSpeed = pid.getOutput(odometryRad, setpointRad);
        telemetry.addData(name + " Rotation speed", rotationSpeed);

        // Calculate the motor speeds
        double[] motorSpeeds = calculateMotorSpeeds(rotationSpeed, vector.getLength());

        motorSpeeds = normalizeWheelSpeeds(motorSpeeds);

        // Set the motor powers
        upperMotor.setPower(motorSpeeds[0]);
        lowerMotor.setPower(motorSpeeds[1]);
        telemetry.addData(name + " Motor Powers (upper | lower)", motorSpeeds[0] + " | " + motorSpeeds[1]);
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
