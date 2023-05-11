package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="test")
public class Test extends LinearOpMode {

    private static DcMotor leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    @Override
    public void runOpMode() {
        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");

        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");

        waitForStart();

        while (opModeIsActive()) {
            leftUpperMotor.setPower(1);
            leftLowerMotor.setPower(-1);
            rightUpperMotor.setPower(1);
            rightLowerMotor.setPower(-1);
        }
    }
}
