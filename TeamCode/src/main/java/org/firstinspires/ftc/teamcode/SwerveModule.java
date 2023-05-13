package org.firstinspires.ftc.teamcode;

import com.mineinjava.quail.differentialSwerveModuleBase;
import com.mineinjava.quail.util.Vec2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SwerveModule extends differentialSwerveModuleBase {
    public MiniPID pid;
    DcMotor upperMotor, lowerMotor;
    Telemetry telemetry;

    public SwerveModule(Vec2d position, double steeringGearRatio, double driveGearRatio, MiniPID pid, DcMotor upperMotor, DcMotor lowerMotor, Telemetry telemetry) {
        super(position, steeringGearRatio, driveGearRatio);
        this.pid = pid;
        this.upperMotor = upperMotor;
        this.lowerMotor = lowerMotor;
        this.telemetry = telemetry;
    }

    /**
     * Sets the module to a given vector
     * @param vector the vector to set the module to
     * @param currentPodAngleDeg the angle of the pod (degrees)
     */
    public void set(Vec2d vector, double currentPodAngleDeg) {

        // If the vector length is 0, set the motor power to 0
        if (vector.getLength() == 0) {
            upperMotor.setPower(0);
            lowerMotor.setPower(0);

            telemetry.addLine("Vector length is 0, setting motor power to 0");

            return;
        }

        // The target angle is the angle of the vector
        double targetDeg = Math.toDegrees(vector.getAngle());
        telemetry.addData("Target angle", targetDeg);

        // The rotation speed is the output of the PID controller
        double rotationSpeed = pid.getOutput(currentPodAngleDeg, targetDeg);
        telemetry.addData("Rotation speed", rotationSpeed);

        // Calculate the motor speeds
        double[] motorSpeeds = calculateMotorSpeeds(rotationSpeed, vector.getLength());

        // Set the motor powers
        upperMotor.setPower(motorSpeeds[0] * 0.5);
        lowerMotor.setPower(motorSpeeds[1] * 0.5);
        telemetry.addData("Motor Powers (upper | lower)", motorSpeeds[0] + " | " + motorSpeeds[1]);
    }
}
