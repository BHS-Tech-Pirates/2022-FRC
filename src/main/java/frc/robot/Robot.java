package frc.robot;

//motorcontrol classes
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import java.lang.Math;
//import com.revrobotics.*;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//control systems
import edu.wpi.first.wpilibj.XboxController;

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
    private final MotorController leftMotor = new PWMSparkMax(0); // PWM0
    private final MotorController rightMotor = new PWMSparkMax(1);// PWM1
    // private final CANSparkMax LeftCamMotor = new CANSparkMax(2, MotorType.kBrushed);




    private final DifferentialDrive BHSBot = new DifferentialDrive(leftMotor, rightMotor); // DifferentialDrive is a drivetrain class
    private final XboxController controller = new XboxController(0);

    private final Timer timer = new Timer();


    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
    leftMotor.setInverted(true);


                           // tank drive
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like
     * diagnostics that you want ran jduring disabled, autonomous, teleoperated and
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
     * uncontrollerent the getString line to get the auto name from the text box below the
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
        //Drive for n seconds
        /*double n = 3.0;
        if (timer.get() < n) {
            BHSBot.tankDrive(0.25, 0.25);
        } else {
            BHSBot.stopMotor();
        }*/
    }

    @Override
    public void teleopInit() {

    }
    private boolean toggleState = false;
    private boolean isPressed = false;
    // private double leftStick =  controller.getRawAxis(1);
    // private double rightStick = controller.getRawAxis(5);

    @Override
    public void teleopPeriodic() {
        // leftStick =  controller.getRawAxis(1);
        // rightStick = controller.getRawAxis(5);
        isPressed = controller.getStartButtonPressed();
        if(isPressed){
            toggleState = !toggleState;
            leftMotor.setInverted(!toggleState);
            rightMotor.setInverted(toggleState);
        }  
        if (toggleState){
            BHSBot.arcadeDrive(-controller.getRawAxis(1)   , -controller.getRawAxis(4), false);

        }
        else{
            BHSBot.arcadeDrive(-controller.getRawAxis(1), controller.getRawAxis(4), false);
        }
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
       // LeftCamMotor.set(1);
        leftMotor.set(1);
        rightMotor.set(1);
    }
}
