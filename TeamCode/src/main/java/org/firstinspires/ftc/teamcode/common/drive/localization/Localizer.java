package org.firstinspires.ftc.teamcode.common.drive.localization;

import com.mineinjava.quail.util.geometry.Pose2d;

public interface Localizer {

    void periodic();

    Pose2d getPos();

    void setPos(Pose2d pose);
}