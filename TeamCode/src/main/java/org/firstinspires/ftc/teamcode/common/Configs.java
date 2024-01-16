package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configs {

    /*
        DEPOSIT
     */

    // Lift
    public static double
    liftBottomPosition = 4.0,
    liftTransferPosition = -0.0,
    liftHangHeightPosition = 16.0;

    // Grippers
    public static double
    leftGripperGrabPosition = 0.25, leftGripperReleasePosition = 0.55,
    rightGripperGrabPosition = 0.27, rightGripperReleasePosition = 0.57;

    // V4B
    public static double
    leftV4bDepositPosition = 0.85, leftV4bTransferPosition = 0.0,
    leftV4bIdlePosition = 0.5, leftV4bDropPosition = 1,
    rightV4bDepositPosition = 0.15, rightV4bTransferPosition = 1,
    rightV4bIdlePosition = 0.5, rightV4bDropPosition = 0;

}
