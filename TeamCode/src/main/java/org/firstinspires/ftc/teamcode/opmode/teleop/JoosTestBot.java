package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.Robot;
import com.amarcolini.joos.control.PIDCoefficients;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.MotorGroup;
import com.amarcolini.joos.hardware.drive.DiffSwerveDrive;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class JoosTestBot extends Robot {

    private PIDCoefficients moduleHeadingPID = new PIDCoefficients(6, 0, 0);
    public MotorGroup leftGroup, rightGroup;
    public DiffSwerveDrive drive;

    public JoosTestBot() {

        leftGroup = new MotorGroup(
                new Motor(hMap, "leftUpperMotor", Motor.Kind.GOBILDA_1150),
                new Motor(hMap, "leftLowerMotor", Motor.Kind.GOBILDA_1150).reversed()
        );
        rightGroup = new MotorGroup(
                new Motor(hMap, "rightUpperMotor", Motor.Kind.GOBILDA_1150),
                new Motor(hMap, "rightLowerMotor", Motor.Kind.GOBILDA_1150).reversed()
        );

        drive = new DiffSwerveDrive(leftGroup, rightGroup, moduleHeadingPID);
        drive.motors.resetEncoder();
        drive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        register(drive);

    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }
}
