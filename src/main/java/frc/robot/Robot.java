package frc.robot;

//smartdashboard classes
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//motorcontrol classes
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
//control systems
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
//drive classes
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//timer classes
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private DifferentialDrive BHSBot; // DifferentialDrive is a drivetrain class
    private Joystick leftStick; // Left joystick
    private Joystick rightStick; // Right joystick

    private final Timer timer = new Timer();

    private final MotorController leftMotor = new PWMSparkMax(0);// PWM0
    private final MotorController rightMotor = new PWMSparkMax(1);// PWM1

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        rightMotor.setInverted(true); // Change this to left if needed. One side needs to be inverted for functional
                                      // tank drive

        BHSBot = new DifferentialDrive(leftMotor, rightMotor);
        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and
     * test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {

    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different
     * autonomous modes using the dashboard. The sendable chooser code works with
     * the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the
     * chooser code and
     * uncomment the getString line to get the auto name from the text box below the
     * Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional comparisons to the
     * switch structure
     * below with additional strings. If using the SendableChooser make sure to add
     * them to the
     * chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        timer.reset();
        timer.start();
    }

    @Override
    public void autonomousPeriodic() {
        // Drive for n seconds
        double n = 3.0;
        if (timer.get() < n) {
            BHSBot.tankDrive(0.25, 0.25);
        } else {
            BHSBot.stopMotor();
        }
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        BHSBot.tankDrive(leftStick.getY(), rightStick.getY());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
}
