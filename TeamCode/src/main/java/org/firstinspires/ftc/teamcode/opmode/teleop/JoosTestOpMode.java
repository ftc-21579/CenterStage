package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.amarcolini.joos.command.CommandOpMode;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.DiffySwerve;

@TeleOp(name = "JoosTestOpMode")
public class JoosTestOpMode extends CommandOpMode {

    @Override
    public void preInit() {

        PhotonCore.enable(); // Lynx module optimizations

        DiffySwerve robot = registerRobot(new DiffySwerve(telem));

        telem.addLine("Ready");

    }
}