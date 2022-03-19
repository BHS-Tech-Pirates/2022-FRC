package frc.robot;

//motorcontrol classes
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
//import com.revrobotics.*; 
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.Encoder;

//control systems
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj.TimedRobot;
//drive classes
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//timer classes
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    //Drive motorrs
    private final MotorController leftMotor = new PWMSparkMax(0); //PWM 0
    private final MotorController rightMotor = new PWMSparkMax(1); //PWM 1

    //Encoded motors & encoders
    private Encoder encoder = new Encoder(2,3); //DIO pins 2 and 3
    private Encoder encoder2 = new Encoder(4,5); //DIO pins 4 and5
    private MotorController encodedMotor = new PWMSparkMax(2); //PWM 2
    private MotorController encodedMotor2 = new PWMSparkMax(3); //PWM 3

    //Speed vars
    private double speedAdjust = 0;
    private final double TURBOSPEED = 1;
    private final double SLOWSPEED = 3;
    private final double MAINSPEED = 1.5;

    //Bot, controller, timer
    private final DifferentialDrive BHSBot = new DifferentialDrive(leftMotor, rightMotor); 
    private final XboxController controller = new XboxController(0);
    private final Timer timer = new Timer();

    //Pnuematics 
    private final Compressor comp = new Compressor(0,PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1); //Channel 0 and 1 on pnuematic controller

    private final Compressor comp2 = new Compressor(0,PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid solenoid2 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3); //Channel 0 and 1 on pnuematic controller


    @Override
    public void robotInit() {
        leftMotor.setInverted(true);
        encoder.reset();
        
            try {
                comp.enableDigital();
                comp2.enableDigital();
            } catch (Exception e) {
                System.out.println("Check connections");
            }
        encoder.setDistancePerPulse((Math.PI * 6) / 360.0);
        encoder2.setDistancePerPulse((Math.PI * 6) / 360.0);
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
        //System.out.println(timer.get());
        double n = 2;
        if (timer.get() < n) {
            BHSBot.arcadeDrive(0.25,0,false);
        } else {
            BHSBot.stopMotor();
        }
    }

    @Override
    public void teleopInit() {
        encoder.reset();
        encoder2.reset();
    }

    private boolean startButtonState = false;
    private boolean startButtonPressed = false;

    private boolean SuckButtonState = false;
    private boolean SuckButtonPressed = false;
    private int leftStickAdjust = 1;

    private boolean SpitButtonState = false;
    private boolean rightStickPressed = false;
    private int rightStickAdjust = 1;

    @Override
    public void teleopPeriodic() {
        //Toggle status
        startButtonPressed = controller.getStartButtonPressed();
        SuckButtonPressed = controller.getLeftStickButtonPressed();
        rightStickPressed = controller.getRightStickButtonPressed();
        //Driving
        if(startButtonPressed){
            startButtonState = !startButtonState;
            leftMotor.setInverted(!startButtonState);
            rightMotor.setInverted(startButtonState);
        }if(controller.getLeftBumper()){
            speedAdjust = TURBOSPEED;
        }else if(controller.getRightBumper()){
            speedAdjust = SLOWSPEED;
        }else{
            speedAdjust = MAINSPEED;
        }if(startButtonState){
            BHSBot.arcadeDrive(-controller.getRawAxis(1)/speedAdjust,-controller.getRawAxis(0)/speedAdjust,false);
        }else{
            BHSBot.arcadeDrive(-controller.getRawAxis(1)/speedAdjust,controller.getRawAxis(0)/speedAdjust,false);
        }
        //Sucking balls😳😳😳😳😳😳😳😳😳😳😳😳😳😳😳 

        if(SuckButtonPressed){
            SuckButtonState = !SuckButtonState;        
        }
        
        if(rightStickPressed){
            SpitButtonState = !SpitButtonState;
        }
  
        if(SpitButtonState != true){
            if(SuckButtonState){
                encodedMotor.set(1);
                encodedMotor2.set(1);
            }else{
                encodedMotor.stopMotor();
                encodedMotor2.stopMotor();
            }
        }
        
        //Spitting balls🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮
            if(SuckButtonState != true){
                if(SuckButtonState){
                    encodedMotor.set(-1);
                    encodedMotor2.set(-1);
                }else{
                    encodedMotor.stopMotor();
                    encodedMotor2.stopMotor();
                }
            }
        //Pnuematics
        if(controller.getPOV() == 0){
            solenoid.set(DoubleSolenoid.Value.kForward);
        }else if(controller.getPOV() == 180){
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(controller.getAButton()){
            solenoid2.set(DoubleSolenoid.Value.kForward);
        }else if(controller.getBButton()){
            solenoid2.set(DoubleSolenoid.Value.kReverse);
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
    }

}
