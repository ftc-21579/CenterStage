package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configs {

    /*
        DEPOSIT
     */

    // Lift
    public static double
    liftBottomPosition = 2.0,
    liftTransferPosition = -1.5,
    liftHangHeightPosition = 21.0;

    // Grippers
    public static double
    leftGripperGrabPosition = 0.35, leftGripperReleasePosition = 0.6,
    rightGripperGrabPosition = 0.25, rightGripperReleasePosition = 0.5;

    // V4B
    public static double
    leftV4bDepositPosition = 0.8, leftV4bTransferPosition = 0.0,
    leftV4bIdlePosition = 0.5, leftV4bDropPosition = 1,
    rightV4bDepositPosition = 0.2, rightV4bTransferPosition = 1,
    rightV4bIdlePosition = 0.5, rightV4bDropPosition = 0;

}
