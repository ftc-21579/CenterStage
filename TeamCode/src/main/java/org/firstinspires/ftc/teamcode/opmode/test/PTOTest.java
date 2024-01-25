package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name = "PTO Test", group = "Test")
public class PTOTest extends LinearOpMode {

    private DcMotor leftMotor, rightMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        leftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        rightMotor = hardwareMap.get(DcMotor.class, "backLeft");

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while(opModeIsActive()) {

            // extension extend
            if (gamepad1.b) {
                leftMotor.setTargetPosition(100);
                rightMotor.setTargetPosition(100);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(1.0);
                rightMotor.setPower(1.0);
            }

            // lift down
            else if (gamepad1.a) {
                leftMotor.setTargetPosition(100);
                rightMotor.setTargetPosition(-100);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(1.0);
                rightMotor.setPower(1.0);
            }

            // lift up
            else if (gamepad1.x) {
                leftMotor.setTargetPosition(-100);
                rightMotor.setTargetPosition(100);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(1.0);
                rightMotor.setPower(1.0);
            }

            // extension retract
            else if (gamepad1.y) {
                leftMotor.setTargetPosition(-100);
                rightMotor.setTargetPosition(-100);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(1.0);
                rightMotor.setPower(1.0);
            }

            else {
                leftMotor.setTargetPosition(0);
                rightMotor.setTargetPosition(0);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //if (gamepad2.b) {
                //leftMotor.setTargetPosition(100);
                //rightMotor.setTargetPosition(100);
                //leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //}

            //else if (gamepad2.a) {
                //leftMotor.setTargetPosition(-100);
                //rightMotor.setTargetPosition(-100);
                //leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //}

            //else {
                //leftMotor.setTargetPosition(0);
                //rightMotor.setTargetPosition(0);
                //leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //}

            telemetry.addData("Left Motor Position: ", leftMotor.getCurrentPosition());
            telemetry.addData("Right Motor Position: ", rightMotor.getCurrentPosition());
            telemetry.addData("Left Motor Target: ", leftMotor.getTargetPosition());
            telemetry.addData("Right Motor Target: ", rightMotor.getTargetPosition());

            telemetry.update();
        }
    }
}
