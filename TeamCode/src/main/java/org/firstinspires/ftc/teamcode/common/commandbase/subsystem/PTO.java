package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@Config
public class PTO {
    Bot bot;
    public static double EXT_TICKS_PER_INCH = 22.78149; // TODO
    public static double LIFT_TICKS_PER_INCH = 0; // TODO

    public static double EXT_MAX_POS = 20; // TODO
    public static double EXT_MIN_POS = 0; // TODO
    public static double LIFT_MAX_POS = 20; // TODO
    public static double LIFT_MIN_POS = 0; // TODO
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

    public void extend(double speed) {
        double current = leftMotor.getCurrentPosition();

        if (current + 10 > EXT_MAX_POS) {
            return;
        }

        leftMotor.setTargetPosition((int) (current + 10));
        rightMotor.setTargetPosition((int) (current + 10));
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
    }

    public void retract(double speed) {
        double current = leftMotor.getCurrentPosition();
        double target = current - 10;

        if (current - 10 < EXT_MIN_POS) {
            target = EXT_MIN_POS;
        }

        leftMotor.setTargetPosition((int) target);
        rightMotor.setTargetPosition((int) target);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
    }

    public void liftUp(double speed) {
        double current = leftMotor.getCurrentPosition();

        if (current + 10 > LIFT_MAX_POS) {
            return;
        }

        leftMotor.setTargetPosition((int) (current + 10) * -1);
        rightMotor.setTargetPosition((int) (current + 10));
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
    }

    public void liftDown(double speed) {
        double current = leftMotor.getCurrentPosition();
        double target = current - 10;

        if (current - 10 < LIFT_MIN_POS) {
            target = LIFT_MIN_POS;
        }

        leftMotor.setTargetPosition((int) target);
        rightMotor.setTargetPosition((int) target * -1);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
    }

    public void liftToBottom(double speed) {
        leftMotor.setTargetPosition((int) LIFT_MIN_POS);
        rightMotor.setTargetPosition((int) LIFT_MIN_POS);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
    }

    public void extensionToEndstops(double speed) {
        leftMotor.setTargetPosition((int) EXT_MIN_POS);
        rightMotor.setTargetPosition((int) EXT_MIN_POS);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(speed);
        rightMotor.setPower(speed);
    }


}
