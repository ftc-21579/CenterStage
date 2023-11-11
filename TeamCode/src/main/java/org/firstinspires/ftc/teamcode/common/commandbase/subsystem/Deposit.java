package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.mineinjava.quail.util.MiniPID;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToTransferCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

@Config
public class Deposit {

    Bot bot;
    public static double liftKp = 1.0, liftKi = 0.0, liftKd = 0.0;
    MiniPID liftPID = new MiniPID(liftKp, liftKi, liftKd);
    public static double TICKS_PER_INCH = 29.29;

    public DepositState state = DepositState.TRANSFER;

    DcMotor depositMotor;

    Servo leftReleaseServo, rightReleaseServo;
    Servo leftV4BServo, rightV4BServo;

    public Deposit(Bot bot) {
        this.bot = bot;

        depositMotor = bot.hMap.get(DcMotor.class, "depositMotor");
        depositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftReleaseServo = bot.hMap.get(Servo.class, "leftReleaseServo");
        rightReleaseServo = bot.hMap.get(Servo.class, "rightReleaseServo");
        leftV4BServo = bot.hMap.get(Servo.class, "depositLeftV4BServo");
        rightV4BServo = bot.hMap.get(Servo.class, "depositRightV4BServo");
    }

    public void toBottomPosition() {
        double liftPower = liftPID.getOutput(depositMotor.getCurrentPosition(), 3 * TICKS_PER_INCH);
        depositMotor.setPower(liftPower);

        bot.telem.addData("Lift Setpoint", 3 * TICKS_PER_INCH);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);

        new ReleasePixelsCommand(this).schedule();
        new DepositV4BToIdleCommand(this).schedule();

        if (depositMotor.getCurrentPosition() > 2.95 * TICKS_PER_INCH && depositMotor.getCurrentPosition() < 3.05 * TICKS_PER_INCH) {
            depositMotor.setPower(0.0);
            state = DepositState.BOTTOM;
        }
    }

    public void toTransferPosition() {
        double liftPower = liftPID.getOutput(depositMotor.getCurrentPosition(), 0.5 * TICKS_PER_INCH);
        depositMotor.setPower(liftPower);

        bot.telem.addData("Lift Power", 0.5 * TICKS_PER_INCH);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);

        new ReleasePixelsCommand(this).schedule();
        new DepositV4BToTransferCommand(this).schedule();

        if (depositMotor.getCurrentPosition() < 0.55 * TICKS_PER_INCH) {
            depositMotor.setPower(0.0);
            state = DepositState.TRANSFER;
        }
    }

    public void grabPixels() {
        leftReleaseServo.setPosition(0.5);
        rightReleaseServo.setPosition(0.5);
    }

    public void releasePixels() {
        leftReleaseServo.setPosition(0.0);
        rightReleaseServo.setPosition(0.0);
    }

    public void toggleLeftPixelServo() {
        if (leftReleaseServo.getPosition() == 0.0) {
            leftReleaseServo.setPosition(0.5);
        } else {
            leftReleaseServo.setPosition(0.0);
        }
    }

    public void toggleRightPixelServo() {
        if (rightReleaseServo.getPosition() == 0.0) {
            rightReleaseServo.setPosition(0.5);
        } else {
            rightReleaseServo.setPosition(0.0);
        }
    }

    public void raiseLift() {
        double liftPower = liftPID.getOutput(depositMotor.getCurrentPosition(), depositMotor.getCurrentPosition() + 10);
        depositMotor.setPower(liftPower);

        bot.telem.addData("Lift Setpoint", (depositMotor.getCurrentPosition() + 10) / TICKS_PER_INCH);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);

        new DepositV4BToDepositCommand(this).schedule();
    }

    public void lowerLift() {
        double liftPower = liftPID.getOutput(depositMotor.getCurrentPosition(), depositMotor.getCurrentPosition() - 10);
        depositMotor.setPower(liftPower);

        bot.telem.addData("Lift Setpoint", (depositMotor.getCurrentPosition() - 10) / TICKS_PER_INCH);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);
    }

    public void stopLift() {
        depositMotor.setPower(0);
    }

    public void v4bToggle() {
        if (leftV4BServo.getPosition() == 0.0) {
            leftV4BServo.setPosition(0.5);
            rightV4BServo.setPosition(0.5);
        } else {
            leftV4BServo.setPosition(0.25);
            rightV4BServo.setPosition(0.25);
        }
    }

    public void v4bToDeposit() {
        leftV4BServo.setPosition(0.5);
        rightV4BServo.setPosition(0.5);
    }

    public void v4bToTransfer() {
        leftV4BServo.setPosition(0.0);
        rightV4BServo.setPosition(0.0);
    }

    public void v4bToIdle() {
        leftV4BServo.setPosition(0.25);
        rightV4BServo.setPosition(0.25);
    }

    public void v4bToDrop() {
        leftV4BServo.setPosition(0.75);
        rightV4BServo.setPosition(0.75);
    }
}
