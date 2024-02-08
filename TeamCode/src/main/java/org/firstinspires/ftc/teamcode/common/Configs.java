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

    // Deposit V4B
    public static double
    leftV4bDepositPosition = 0.85, leftV4bTransferPosition = 0.0,
    leftV4bIdlePosition = 0.5, leftV4bDropPosition = 1.0,
    rightV4bDepositPosition = 0.15, rightV4bTransferPosition = 1.0,
    rightV4bIdlePosition = 0.5, rightV4bDropPosition = 0.0;


    /*
        INTAKE
     */
    public static double
    intakeAboveStackPosition = 0.87, intakeIntakePosition = 1.0,
    intakeTransferPosition = 0.0, intakeAboveTransferPosition = 0.05;

    /*
        PTO
     */
    public static int
    extensionMaxPos = 50000, extensionMinPos = 0,
    liftMaxPos = 50000, liftMinPos = 0;

    public static double
    liftIncrement = 1, extensionIncrement = 1,
    liftSpeed = 0.5, extensionSpeed = 0.5,
    LIFT_TICKS_PER_INCH = 0, EXTENSION_TICKS_PER_INCH = 22.78149; // TODO: THESE VALUES ASAP
}
