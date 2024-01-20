package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configs {

    /*
        DEPOSIT
     */

    // Lift
    public static double
    liftTransferPosition = 0.0,
    liftAutonBackdropPosition = 1.0,
    liftBottomPosition = 4.0,
    liftHangHeightPosition = 18.0;

    // Grippers
    public static double
    leftGripperGrabPosition = 0.25, leftGripperReleasePosition = 0.55,
    rightGripperGrabPosition = 0.27, rightGripperReleasePosition = 0.57;

    // V4B
    public static double
    leftV4bDepositPosition = 0.9, leftV4bTransferPosition = 0.0,
    leftV4bIdlePosition = 0.5, leftV4bDropPosition = 1.0,
    rightV4bDepositPosition = 0.1, rightV4bTransferPosition = 1.0,
    rightV4bIdlePosition = 0.5, rightV4bDropPosition = 0.0;

}
