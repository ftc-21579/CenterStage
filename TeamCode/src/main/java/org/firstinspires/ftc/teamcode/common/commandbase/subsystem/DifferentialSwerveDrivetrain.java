package org.firstinspires.ftc.teamcode.common.commandbase.subsystem;

//import org.firstinspires.ftc.teamcode.common.drive.drive.swerve.SwerveModule;


/*
@Config
public class DifferentialSwerveDrivetrain extends SubsystemBase {
    Bot bot;

    public static final double steeringGearRatio = 4;
    public static final double driveGearRatio = 1;
    private DcMotorEx leftUpperMotor, leftLowerMotor, rightLowerMotor, rightUpperMotor;

    private AbsoluteAnalogEncoder leftAbsoluteEncoder, rightAbsoluteEncoder;

    public static double leftkp = 2.5, leftki = 0.0, leftkd = 2.0;
    public static double rightkp = 2.5, rightki = 0.0, rightkd = 2.0;

    private MiniPID leftPID = new MiniPID(leftkp, leftki, leftkd);
    private MiniPID rightPID = new MiniPID(rightkp, rightki, rightkd);

    public static double headingkp = 1.0, headingki = 0.0, headingkd = 0.0;
    private MiniPID headingPID = new MiniPID(headingkp, headingki, headingkd);
    private int headingLockTarget = 0;
    private SwerveModule left, right;
    private SwerveDrive<SwerveModule> drive;
    private final List<SwerveModule> modules = new ArrayList<>();
    private PathFollower pathFollower;
    private boolean fieldCentric = false;
    private boolean headingLock = false;

    Path emptyPath = new Path(new ArrayList<Pose2d>(
            Arrays.asList(
                    new Pose2d(0, 0, 0)
            )
    ));


    /**
     * Encapsulates the drivetrain subsystem commands
     * @param bot
     *
    public DifferentialSwerveDrivetrain(Bot bot) {
        this.bot = bot;

        // Initialize the motors
        leftUpperMotor = bot.hMap.get(DcMotorEx.class, "leftUpperMotor");
        leftLowerMotor = bot.hMap.get(DcMotorEx.class, "leftLowerMotor");
        rightLowerMotor = bot.hMap.get(DcMotorEx.class, "rightLowerMotor");
        rightUpperMotor = bot.hMap.get(DcMotorEx.class, "rightUpperMotor");

        // Initialize the absolute encoders
        leftAbsoluteEncoder = new AbsoluteAnalogEncoder(bot.hMap.get(AnalogInput.class, "leftEncoder"));
        rightAbsoluteEncoder = new AbsoluteAnalogEncoder(bot.hMap.get(AnalogInput.class, "rightEncoder"));

        leftLowerMotor.setDirection(DcMotor.Direction.REVERSE);
        rightLowerMotor.setDirection(DcMotor.Direction.REVERSE);

        leftLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLowerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightUpperMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initialize the swerve modules
        left = new SwerveModule(new Vec2d(-0.454, 0),
                steeringGearRatio,
                driveGearRatio,
                leftPID,
                leftUpperMotor,
                leftLowerMotor,
                leftAbsoluteEncoder,
                bot.telem,
                "Left"
        );

        right = new SwerveModule(new Vec2d(0.454, 0),
                steeringGearRatio,
                driveGearRatio,
                rightPID,
                rightUpperMotor,
                rightLowerMotor,
                rightAbsoluteEncoder,
                bot.telem,
                "Right"
        );

        // Add the modules to the list of modules
        modules.add(left);
        modules.add(right);

        // Initialize the swerve drive class
        drive = new SwerveDrive<>(modules);

        // Initialize the path follower
        pathFollower = new PathFollower((Localizer) bot.getLocalizer(),
                emptyPath,
                0.5,
                0.5,
                0.5,
                0.5,
                new MiniPID(1, 0, 0),
                1.0);
    }

    /** The standard drive command for teleop, supports field centric if fieldCentric
     *
    public void teleopDrive(Vec2d leftStick, double rot, double multiplier) {
        double x = -leftStick.x * multiplier;
        double y = -leftStick.y * multiplier;

        rot *= multiplier;

        double botHeading = bot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        if (headingLock) {
            rot = headingPID.getOutput(botHeading, headingLockTarget);
            bot.telem.addData("Heading Lock Target", headingLockTarget);
            bot.telem.addData("Heading Lock Current IMU", botHeading);
        }

        if (fieldCentric) {
            drive.move(new RobotMovement(rot, new Vec2d(y, x)), -botHeading);
        } else {
            drive.move(new RobotMovement(rot, new Vec2d(y, x)), 0);
        }
    }

    /**
     * Updates the localizer of the Drivetrain
     *
    public void updateLocalizer() {
        bot.getLocalizer().periodic();

        Pose2d current = bot.getLocalizer().getPos();

        bot.telem.addData("Pose X", new DecimalFormat("#.##").format(current.x) + " inches");
        bot.telem.addData("Pose Y", new DecimalFormat("#.##").format(current.y) + " inches");
        bot.telem.addData("Pose Heading", new DecimalFormat("#.##").format(current.heading) + " radians");

        bot.telem.addData("Left Pod Heading", leftAbsoluteEncoder.getCurrentPosition());
        bot.telem.addData("Right Pod Heading", rightAbsoluteEncoder.getCurrentPosition());

        double upperPosRad = rightUpperMotor.getCurrentPosition() / 145.1 * (Math.PI * 2);
        double lowerPosRad = rightLowerMotor.getCurrentPosition() / 145.1 * (Math.PI * 2);

        double podRad = (upperPosRad + lowerPosRad) / 2;

        double odometryRad = podRad / 4;
        bot.telem.addData("left odometry", odometryRad);
    }

    public void setPath(Path p) {
        pathFollower.setPath(p);
    }

    public void followPath(Path p) {
        bot.telem.addData("Path Finished", pathFinished());
        bot.telem.addData("Next Point", pathFollower.path.getNextPoint());

        if (p != pathFollower.path) {
            pathFollower.setPath(p);
        }

        if (!pathFinished()) {

            robotMovement nextDriveMovement = pathFollower.calculateNextDriveMovement();
            bot.telem.addData("Next Drive Movement",
                    "X: " + nextDriveMovement.translation.x
                            + " Y: " + nextDriveMovement.translation.y
                            + " Rot: " + nextDriveMovement.rotation);
            robotMovement newMovement = new robotMovement(nextDriveMovement.rotation,
                    new Vec2d(nextDriveMovement.translation.y, nextDriveMovement.translation.x));
            drive.move(nextDriveMovement, 0);
        }
    }

    public boolean pathFinished() {
        return pathFollower.isFinished();
    }

    public void toggleHeadingLock() {
        headingLock = !headingLock;
        bot.telem.addData("Heading Lock", headingLock);
    }

    public void rotateHeadingLock() {
        headingLockTarget += Math.PI / 2;
        if (headingLockTarget > 2 * Math.PI) {
            headingLockTarget -= 2 * Math.PI;
        }
    }

    public void toggleFieldCentric() {
        fieldCentric = !fieldCentric;
    }
}
*/