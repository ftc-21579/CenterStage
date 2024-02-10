package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.centerstage.GripperState;
import org.firstinspires.ftc.teamcode.common.Bot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Config
public class Deposit {

    Bot bot;
    Servo leftReleaseServo, rightReleaseServo, leftV4BServo, rightV4BServo;

    public Deposit(Bot bot) {
        this.bot = bot;
        leftReleaseServo = bot.hMap.get(Servo.class, "leftReleaseServo");
        rightReleaseServo = bot.hMap.get(Servo.class, "rightReleaseServo");
        leftV4BServo = bot.hMap.get(Servo.class, "depositLeftV4BServo");
        rightV4BServo = bot.hMap.get(Servo.class, "depositRightV4BServo");

        //pixelTfodProcessor = new TfodProcessor.Builder()
        //        .setModelAssetName("pixel_model.tflite")
        //       .setModelLabels(new String[] {
        //                "green", "purple", "white", "yellow"
        //        })
        //        .build();

        //visionPortal = new VisionPortal.Builder()
        //        .setCamera(bot.hMap.get(WebcamName.class, "DepositCam"))
        //        .setCameraResolution(new Size(1024, 576))
        //        .addProcessor(pixelTfodProcessor)
        //        .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
        //        .enableLiveView(true)
        //        .setAutoStopLiveView(true)
        //        .build();
        //visionPortal.setProcessorEnabled(pixelTfodProcessor, false);
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
     * get the left gripper's position
     * @return the position the left gripper is set to
     */
    public double getLeftGripperPosition() {
        return leftReleaseServo.getPosition();
    }
    /**
     * get the right gripper's position
     * @return the position the right gripper is set to
     */
    public double getRightGripperPosition() {
        return rightReleaseServo.getPosition();
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
}