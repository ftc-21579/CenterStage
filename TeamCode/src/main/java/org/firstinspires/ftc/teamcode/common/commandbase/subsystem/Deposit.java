package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.mineinjava.quail.util.MiniPID;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToIdleCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToTransferCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Config
public class Deposit {

    Bot bot;
    public static double liftKp = 0.001, liftKi = 0.0, liftKd = 0.0;
    MiniPID liftPID = new MiniPID(liftKp, liftKi, liftKd);
    public static double TICKS_PER_INCH = 108.62;

    public DepositState state = DepositState.TRANSFER;
    public static double liftSetpoint = 0.0;

    public static double gripperGrabPosition = 0.0, gripperReleasePosition = 0.5;
    public static double v4bDepositPosition = 0.5, v4bTransferPosition = 0.0, v4bIdlePosition = 0.25, v4bDropPosition = 0.75;

    DcMotor depositMotor;

    Servo leftReleaseServo, rightReleaseServo;
    Servo leftV4BServo, rightV4BServo;

    public VisionPortal visionPortal;
    public TfodProcessor pixelTfodProcessor;

    public Deposit(Bot bot) {
        this.bot = bot;

        depositMotor = bot.hMap.get(DcMotor.class, "depositMotor");
        depositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        depositMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        depositMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftReleaseServo = bot.hMap.get(Servo.class, "leftReleaseServo");
        rightReleaseServo = bot.hMap.get(Servo.class, "rightReleaseServo");
        leftV4BServo = bot.hMap.get(Servo.class, "depositLeftV4BServo");
        rightV4BServo = bot.hMap.get(Servo.class, "depositRightV4BServo");

        pixelTfodProcessor = new TfodProcessor.Builder()
                .setModelAssetName("pixel_model.tflite")
                .setModelLabels(new String[] {
                        "green", "purple", "white", "yellow"
                })
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(bot.hMap.get(WebcamName.class, "DepositCam"))
                .setCameraResolution(new Size(1024, 576))
                .addProcessor(pixelTfodProcessor)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();
        visionPortal.setProcessorEnabled(pixelTfodProcessor, false);
    }

    public void toBottomPosition() {
        this.liftSetpoint = 3.0;

        new ReleasePixelsCommand(this).schedule();
        new DepositV4BToIdleCommand(this).schedule();

        if (depositMotor.getCurrentPosition() > 2.95 * TICKS_PER_INCH && depositMotor.getCurrentPosition() < 3.05 * TICKS_PER_INCH) {
            stopLift();
            state = DepositState.BOTTOM;
        }
    }

    public void toTransferPosition() {
        this.liftSetpoint = 0.1;

        new ReleasePixelsCommand(this).schedule();
        new DepositV4BToTransferCommand(this).schedule();

        if (depositMotor.getCurrentPosition() < 0.25 * TICKS_PER_INCH) {
            stopLift();
            state = DepositState.TRANSFER;
        }
    }

    public void grabPixels() {
        leftReleaseServo.setPosition(gripperGrabPosition);
        rightReleaseServo.setPosition(gripperGrabPosition);
    }

    public void releasePixels() {
        leftReleaseServo.setPosition(gripperReleasePosition);
        rightReleaseServo.setPosition(gripperReleasePosition);
    }

    public void toggleLeftPixelServo() {
        if (leftReleaseServo.getPosition() == gripperGrabPosition) {
            leftReleaseServo.setPosition(gripperReleasePosition);
        } else {
            leftReleaseServo.setPosition(gripperGrabPosition);
        }
    }

    public void toggleRightPixelServo() {
        if (rightReleaseServo.getPosition() == gripperGrabPosition) {
            rightReleaseServo.setPosition(gripperReleasePosition);
        } else {
            rightReleaseServo.setPosition(gripperGrabPosition);
        }
    }

    public void raiseLift() {
        if (depositMotor.getCurrentPosition() >= 21 * TICKS_PER_INCH) {
            liftSetpoint = liftSetpoint;
        } else {
            this.liftSetpoint = clamp(liftSetpoint + 0.5, 0.1, 21.1);
        }

        new DepositV4BToDepositCommand(this).schedule();
    }

    public void lowerLift() {
        if (depositMotor.getCurrentPosition() <= 0.1 * TICKS_PER_INCH) {
            liftSetpoint = liftSetpoint;
        } else {
            this.liftSetpoint = clamp(liftSetpoint - 0.5, 0.1, 21.1);
        }
    }

    public void runLiftPID() {
        double liftPower = liftPID.getOutput(depositMotor.getCurrentPosition(), clamp(liftSetpoint, 0.1, 21.1) * TICKS_PER_INCH);
        depositMotor.setPower(clamp(liftPower, -1.0, 1.0));

        bot.telem.addData("Lift Setpoint", liftSetpoint);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);
    }

    public void stopLift() {
        depositMotor.setPower(0.0);
    }

    public void v4bToggle() {
        if (leftV4BServo.getPosition() == v4bDepositPosition) {
            leftV4BServo.setPosition(v4bIdlePosition);
            rightV4BServo.setPosition(v4bIdlePosition);
        } else {
            leftV4BServo.setPosition(v4bDepositPosition);
            rightV4BServo.setPosition(v4bDepositPosition);
        }
    }

    public void v4bToDeposit() {
        leftV4BServo.setPosition(v4bDepositPosition);
        rightV4BServo.setPosition(v4bDepositPosition);
    }

    public void v4bToTransfer() {
        leftV4BServo.setPosition(v4bTransferPosition);
        rightV4BServo.setPosition(v4bTransferPosition);
    }

    public void v4bToIdle() {
        leftV4BServo.setPosition(v4bIdlePosition);
        rightV4BServo.setPosition(v4bIdlePosition);
    }

    public void v4bToDrop() {
        leftV4BServo.setPosition(v4bDropPosition);
        rightV4BServo.setPosition(v4bDropPosition);
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
