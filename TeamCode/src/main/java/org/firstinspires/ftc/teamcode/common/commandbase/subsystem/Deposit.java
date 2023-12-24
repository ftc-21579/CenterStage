package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
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
    private double liftSetpoint = 0.0;
    DcMotor depositMotor, otherDepositMotor;
    Servo leftReleaseServo, rightReleaseServo, leftV4BServo, rightV4BServo;
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
                .setCameraResolution(new Size(640, 480))
                //.addProcessor(pixelTfodProcessor)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .build();
        //visionPortal.setProcessorEnabled(pixelTfodProcessor, true);
    }

    /**
     * Sets the left gripper's position
     * @param position the position to set the left gripper to
     */
    public void setLeftGripperPosition(double position) {
        leftReleaseServo.setPosition(position);
    }
    /**
     * Sets the right gripper's position
     * @param position the position to set the right gripper to
     */
    public void setRightGripperPosition(double position) {
        rightReleaseServo.setPosition(position);
    }
    /**
     * Sets the left V4B's position
     * @param position the position to set the left V4B to
     */
    public void setLeftV4BPosition(double position) {
        leftV4BServo.setPosition(position);
    }
    /**
     * Gets the left V4B's position
     * @return the position the left V4B is set to
     */
    public double getLeftV4BPosition() {
        return leftV4BServo.getPosition();
    }
    /**
     * Sets the right V4B's position
     * @param position the position to set the right V4B to
     */
    public void setRightV4BPosition(double position) {
        rightV4BServo.setPosition(position);
    }
    /**
     * Sets the lift's setpoint/target position
     * @param position the position to set the lift to
     */
    public void setLiftTarget(double position) {
        this.liftSetpoint = position;
    }
    /**
     * Gets the lift target position
     * @return the lift target
     */
    public double getLiftTarget() {
        return liftSetpoint;
    }
    /**
     * Gets the lift's current position (in inches)
     * @return the lift's current position (in)
     */
    public double getLiftPosition() {
        return depositMotor.getCurrentPosition() / TICKS_PER_INCH;
    }
    /**
     * Gets the lift's current state (DepositState)
     * @return the lift's current state
     */
    public DepositState getLiftState() {
        return state;
    }
    /**
     * Sets the lift's current state (DepositState)
     * @param state the state to set the lift to
     */
    public void setLiftState(DepositState state) {
        this.state = state;
    }
    /**
     * Run the PID loop for the lift
     */
    public void runLiftPID() {
        double liftPower = liftPID.calculate(depositMotor.getCurrentPosition(), this.liftSetpoint * TICKS_PER_INCH);
        depositMotor.setPower(liftPower);
        otherDepositMotor.setPower(liftPower);

        bot.telem.addData("Lift Setpoint", liftSetpoint);
        bot.telem.addData("Lift Position", depositMotor.getCurrentPosition() / TICKS_PER_INCH);
    }

    public void telemCameraState() {
        bot.telem.addData("Camera State", visionPortal.getCameraState());
    }
}
