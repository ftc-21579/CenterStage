package org.firstinspires.ftc.teamcode.common.commandbase.command.state;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.centerstage.BotState;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToDepositCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.DepositV4BToTransferCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.GrabPixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.deposit.ReleasePixelsCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.DisableIntakeSpinnerCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeAboveTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.intake.IntakeTransferPositionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.command.pto.ResetPTOCommand;
import org.firstinspires.ftc.teamcode.common.Bot;

@Config
public class ToTransferStateCommand extends CommandBase {
    private final Bot bot;
    private final ElapsedTime timer = new ElapsedTime();
    private boolean ready = false;
    public static int delay1 = 500;
    public static int delay2 = 1500;
    public static int delay3 = 2000;

    public ToTransferStateCommand(Bot bot) {
        this.bot = bot;
        timer.reset();
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        if (bot.getBotState() == BotState.TRANSFER) {
            ready = true;
        } else if (bot.getBotState() == BotState.INTAKE) {

            bot.telem.addData("Time: ", timer.milliseconds());

            if (timer.milliseconds() < delay1) {
                new IntakeAboveTransferPositionCommand(bot.intake).schedule();
                new ResetPTOCommand(bot.pto).schedule();
                //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).schedule();
                new ReleasePixelsCommand(bot.deposit).schedule();

            } else if (timer.milliseconds() > delay1 && timer.milliseconds() < delay2) {
                new DepositV4BToTransferCommand(bot.deposit).execute();

            } else if (timer.milliseconds() > delay2 && timer.milliseconds() < delay3) {
                new IntakeTransferPositionCommand(bot.intake).schedule();

            } else {
                new IntakeTransferPositionCommand(bot.intake).schedule();
                new DisableIntakeSpinnerCommand(bot.intake).schedule();
                new GrabPixelsCommand(bot.deposit).schedule();

                ready = true;
            }
        } else if (bot.getBotState() == BotState.DEPOSIT || bot.getBotState() == BotState.ENDGAME) {
            new ResetPTOCommand(bot.pto).schedule();
            //new CustomLiftPositionCommand(bot.pto, Configs.liftTransferPosition).schedule();
            new DepositV4BToDepositCommand(bot.deposit).schedule();

            ready = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (ready) {bot.toTransferState();}
        return ready;
    }

}
