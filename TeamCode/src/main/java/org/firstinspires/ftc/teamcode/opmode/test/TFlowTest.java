package org.firstinspires.ftc.teamcode.opmode.test;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@TeleOp(name="TFlow Test")
public class TFlowTest extends LinearOpMode {

    private TfodProcessor pixelTfodProcessor;
    private VisionPortal visionPortal;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        pixelTfodProcessor = new TfodProcessor.Builder()
                .setModelAssetName("pixel_model.tflite")
               .setModelLabels(new String[] {
                        "green", "purple", "white", "yellow"
                })
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "DepositCam"))
                .setCameraResolution(new Size(1024, 576))
                .addProcessor(pixelTfodProcessor)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                //.setAutoStopLiveView(true)
                .build();
        //visionPortal.setProcessorEnabled(pixelTfodProcessor, false);

        waitForStart();

        while (opModeIsActive()) {

            List<Recognition> currentRecognitions = pixelTfodProcessor.getRecognitions();
            telemetry.addData("Backdrop Recognitions", currentRecognitions.size());

            pixelTfodProcessor.getRecognitions();
            for (Recognition r : currentRecognitions) {
                telemetry.addLine(String.valueOf(r.getTop()));
            }

            telemetry.addData("FPS", visionPortal.getFps());

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
