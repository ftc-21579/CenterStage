package org.firstinspires.ftc.teamcode.common.commandbase.command.deposit;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.common.centerstage.DepositState;
import org.firstinspires.ftc.teamcode.common.commandbase.subsystem.Deposit;
import org.firstinspires.ftc.teamcode.common.drive.drive.Bot;

import java.util.List;

@Config
public class DepositAutomaticHeightCommand extends CommandBase {
    private Bot bot;
    private Deposit deposit;
    private boolean ready = false;
    public static int height = 200;

    public DepositAutomaticHeightCommand(Bot bot) {
        this.bot = bot;
        this.deposit = bot.deposit;
    }

    @Override
    public void initialize() {
        deposit.visionPortal.setProcessorEnabled(deposit.pixelTfodProcessor, true);
        bot.telem.addLine("AutoLift Init");
        deposit.state = DepositState.AUTOMATIC_LIFTING;
    }

    @Override
    public void execute() {
        List<Recognition> currentRecognitions = deposit.pixelTfodProcessor.getRecognitions();
        bot.telem.addData("Backdrop Recognitions", currentRecognitions.size());

        boolean needsToBeLifted = false;
        if (currentRecognitions.size() != 0) {
            for (Recognition r : currentRecognitions) {
                bot.telem.addLine(String.valueOf(r.getTop()));
                if (r.getTop() > height) {
                    ready = false;
                    needsToBeLifted = true;
                    break;
                }
            }

            if (needsToBeLifted) {
                new ManualLiftUpCommand(deposit).schedule();
            } else {
                new DepositStopLiftCommand(deposit).schedule();
                ready = true;
            }
        } else {
            ready = true;
        }
    }

    @Override
    public boolean isFinished() {
        if(ready) {
            bot.telem.addLine("AutoLift done");
        }
        return ready;
    }
}
