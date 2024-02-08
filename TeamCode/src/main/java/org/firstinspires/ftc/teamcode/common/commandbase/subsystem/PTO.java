package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Bot;

@Config
public class PTO {
    Bot bot;
    public DcMotor leftMotor, rightMotor;

    public PTO(Bot bot) {
        this.bot = bot;

        leftMotor = bot.hMap.get(DcMotor.class, "leftPTO");
        rightMotor = bot.hMap.get(DcMotor.class, "rightPTO");

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Set the motors to a certain power and target position
     * @param power the power to set the motors to (speed)
     * @param leftTarget the target position for the left motor (ticks)
     * @param rightTarget the target position for the right motor (ticks)
     */
    public void setMotors(double power, double leftTarget, double rightTarget) {
        leftMotor.setTargetPosition((int) (leftTarget));
        rightMotor.setTargetPosition((int) (rightTarget));

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    /**
     * Get the current positions of the motors
     * @return an array of the current positions of the motors [left, right]
     */
    public int[] getPositions() {
        return new int[] {leftMotor.getCurrentPosition(), rightMotor.getCurrentPosition()};
    }
}