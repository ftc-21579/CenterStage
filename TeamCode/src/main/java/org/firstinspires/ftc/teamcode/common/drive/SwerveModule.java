package org.firstinspires.ftc.teamcode.common.drive;

import static com.mineinjava.quail.util.util.deltaAngle;

import com.amarcolini.joos.dashboard.SuperTelemetry;
import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.MiniPID;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

            telemetry.addLine(name + " Vector length is 0, setting motor power to 0");

            return;
        }

        // The position of the pod in radians
        double odometryRad = encoder.getCurrentPosition();
        telemetry.addData(name + " Odometry angle", Math.toDegrees(odometryRad));

        // The target angle is the angle of the vector
        double targetRad = vector.getAngle();
        telemetry.addData(name + " Target angle", Math.toDegrees(targetRad));

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
        telemetry.addData(name + " Rotation speed", rotationSpeed);

        // Calculate the motor speeds
        double[] motorSpeeds = calculateMotorSpeeds(rotationSpeed, vector.getLength() * wheelFlipper);
        telemetry.addData(name + " Non-normalized Powers (upper | lower)", motorSpeeds[0] + " | " + motorSpeeds[1]);


        motorSpeeds[0] = motorSpeeds[0] * driveRatio;
        motorSpeeds[1] = motorSpeeds[1] * driveRatio;

        telemetry.addData(name + " Ratio Powers (upper | lower)", motorSpeeds[0] + " | " + motorSpeeds[1]);

        motorSpeeds[0] *= 2;
        motorSpeeds[1] *= 2;

        telemetry.addData(name + " Multiplied Powers (upper | lower)", motorSpeeds[0] + " | " + motorSpeeds[1]);

        motorSpeeds = normalizeWheelSpeeds(motorSpeeds);

        // Set the motor powers
        upperMotor.setPower(motorSpeeds[0]);
        lowerMotor.setPower(motorSpeeds[1]);
        telemetry.addData(name + " Normalized Powers (upper | lower)", motorSpeeds[0] + " | " + motorSpeeds[1]);
    }

    public double[] normalizeWheelSpeeds(double[] speeds) {
        if (largestAbsolute(speeds) > 1) {
            double max = largestAbsolute(speeds);
            for (int i = 0; i < speeds.length; i++){
                speeds[i] /= max;
            }
        }
        return speeds;
    }

    //public double[] normalizeWheelSpeeds(double[] speeds) {
        //double largestAbs = Math.max(Math.abs(largest(speeds)), 1.0); // Find the largest absolute value among speeds

        //for (int i = 0; i < speeds.length; i++) {
            //speeds[i] /= largestAbs; // Normalize each speed by dividing it by the largest absolute value
        //}

        //return speeds;
    //}

    //private double largest(double[] arr) {
        //double largest = 0;
        //for (double d : arr) {
            //if (d > largest) {
                //largest = d;
            //}
        //}
        //return largest;
    //}

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
