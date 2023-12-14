package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.mineinjava.quail.util.MiniPID;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
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
    public static double liftKp = 0.005, liftKi = 0.0, liftKd = 0.0, liftKf = 0.0;
    PIDFController liftPID = new PIDFController(liftKp, liftKi, liftKd, liftKf);
    public static double TICKS_PER_INCH = 121.94;

    public DepositState state = DepositState.TRANSFER;
    public GripperState leftGripper = GripperState.RELEASE, rightGripper = GripperState.RELEASE;
    public double liftSetpoint = 0.0;

    public static double leftGripperGrabPosition = 0.35, leftGripperReleasePosition = 0.6;
    public static double rightGripperGrabPosition = 0.25, rightGripperReleasePosition = 0.5;
    public static double leftV4bDepositPosition = 0.8, leftV4bTransferPosition = 0.0, leftV4bIdlePosition = 0.5, leftV4bDropPosition = 1;
    public static double rightV4bDepositPosition = 0.2, rightV4bTransferPosition = 1, rightV4bIdlePosition = 0.5, rightV4bDropPosition = 0;

    DcMotor depositMotor, otherDepositMotor;

    Servo leftReleaseServo, rightReleaseServo;
    Servo leftV4BServo, rightV4BServo;

    public VisionPortal visionPortal;
    public TfodProcessor pixelTfodProcessor;

    public Deposit(Bot bot) {
        this.bot = bot;

        depositMotor = bot.hMap.get(DcMotor.class, "depositMotor");
        otherDepositMotor = bot.hMap.get(DcMotor.class, "otherDepositMotor");
        otherDepositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        otherDepositMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        depositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        depositMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        otherDepositMotor.setDirection(DcMotor.Direction.REVERSE);
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
        this.liftSetpoint = 1.0;

        new ReleasePixelsCommand(this).schedule();
        new DepositV4BToIdleCommand(this).schedule();

        if (depositMotor.getCurrentPosition() > 1.95 * TICKS_PER_INCH && depositMotor.getCurrentPosition() < 2.05 * TICKS_PER_INCH) {
            state = DepositState.BOTTOM;
        }
    }

    public void toTransferPosition() {
        this.liftSetpoint = 0.0;

        //releasePixels();

        if (depositMotor.getCurrentPosition() < 0.4 * TICKS_PER_INCH) {
            state = DepositState.TRANSFER;
        }
    }

    public void grabPixels() {
        leftReleaseServo.setPosition(leftGripperGrabPosition);
        rightReleaseServo.setPosition(rightGripperGrabPosition);

        leftGripper = GripperState.GRAB;
        rightGripper = GripperState.GRAB;
    }

    public void releasePixels() {
        leftReleaseServo.setPosition(leftGripperReleasePosition);
        rightReleaseServo.setPosition(rightGripperReleasePosition);

        leftGripper = GripperState.RELEASE;
        rightGripper = GripperState.RELEASE;
    }

    public void toggleLeftPixelServo() {
        if (leftGripper == GripperState.GRAB) {
            leftReleaseServo.setPosition(leftGripperReleasePosition);
            leftGripper = GripperState.RELEASE;
        } else {
            leftReleaseServo.setPosition(leftGripperGrabPosition);
            leftGripper = GripperState.GRAB;
        }
    }

    public void toggleRightPixelServo() {
        if (rightGripper == GripperState.GRAB) {
            rightReleaseServo.setPosition(rightGripperReleasePosition);
            rightGripper = GripperState.RELEASE;
        } else {
            rightReleaseServo.setPosition(rightGripperGrabPosition);
            rightGripper = GripperState.GRAB;
        }
        bot.telem.addData("Right Gripper Position", rightReleaseServo.getPosition());
    }

    public void raiseLift() {
        if (depositMotor.getCurrentPosition() >= 21 * TICKS_PER_INCH) {
            this.liftSetpoint = this.liftSetpoint;
        } else {
            this.liftSetpoint = clamp(liftSetpoint + 1.5, 0.1, 21.1);
        }

        if (depositMotor.getCurrentPosition() >= 5.0 * TICKS_PER_INCH) {
            new DepositV4BToDepositCommand(this).schedule();
        }
    }

    public void lowerLift() {
        if (depositMotor.getCurrentPosition() <= 0.1 * TICKS_PER_INCH) {
            this.liftSetpoint = this.liftSetpoint;
        } else {
            this.liftSetpoint = clamp(liftSetpoint - 1.5, 0.1, 21.1);
        }
    }

    public void runLiftPID() {
        double liftPower = liftPID.calculate(depositMotor.getCurrentPosition(), this.liftSetpoint * TICKS_PER_INCH);
        depositMotor.setPower(liftPower);
        otherDepositMotor.setPower(liftPower);

        bot.telem.addData("Lift Setpoint", liftSetpoint);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);
    }

    public void stopLift() {
        depositMotor.setPower(0.0);
    }

    public void v4bToggle() {
        if (leftV4BServo.getPosition() == leftV4bDepositPosition) {
            leftV4BServo.setPosition(leftV4bTransferPosition);
            rightV4BServo.setPosition(rightV4bTransferPosition);
        } else {
            leftV4BServo.setPosition(leftV4bDepositPosition);
            rightV4BServo.setPosition(rightV4bDepositPosition);
        }
        bot.telem.addData("Left V4B Position", leftV4BServo.getPosition());
        bot.telem.addData("Right V4B Position", rightV4BServo.getPosition());
    }

    public void v4bToDeposit() {
        leftV4BServo.setPosition(leftV4bDepositPosition);
        rightV4BServo.setPosition(rightV4bDepositPosition);
    }

    public void v4bToTransfer() {
        leftV4BServo.setPosition(leftV4bTransferPosition);
        rightV4BServo.setPosition(rightV4bTransferPosition);
    }

    public void v4bToIdle() {
        leftV4BServo.setPosition(leftV4bIdlePosition);
        rightV4BServo.setPosition(rightV4bIdlePosition);
    }

    public void v4bToDrop() {
        leftV4BServo.setPosition(leftV4bDropPosition);
        rightV4BServo.setPosition(rightV4bDropPosition);
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
