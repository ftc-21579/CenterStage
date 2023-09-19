package org.firstinspires.ftc.teamcode.opmode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import org.firstinspires.ftc.teamcode.common.hardware.AbsoluteAnalogEncoder;

@Disabled
@TeleOp(name="TestLamprey")
public class TestLamprey extends LinearOpMode {
    private AbsoluteAnalogEncoder leftAbsoluteEncoder, rightAbsoluteEncoder;

    @Override
    public void runOpMode() {
        leftAbsoluteEncoder = new AbsoluteAnalogEncoder(hardwareMap.get(AnalogInput.class, "leftEncoder"));
        rightAbsoluteEncoder = new AbsoluteAnalogEncoder(hardwareMap.get(AnalogInput.class, "rightEncoder"));

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Left Voltage", leftAbsoluteEncoder.getVoltage());
            telemetry.addData("Right Voltage", rightAbsoluteEncoder.getVoltage());
            telemetry.addData("Left Position", Math.toDegrees(leftAbsoluteEncoder.getCurrentPosition()));
            telemetry.addData("Right Position", Math.toDegrees(rightAbsoluteEncoder.getCurrentPosition()));

            telemetry.update();
        }
    }
}
