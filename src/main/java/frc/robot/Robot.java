package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
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

    //Encoded motors & suckEncoders
    private Encoder suckEncoder = new Encoder(0,1); //DIO pins 0 and 1
    private Encoder conveyorEncoder = new Encoder(2,3); //DIO pins 2 and 3
    private MotorController suckMotor = new PWMSparkMax(2); //PWM 2
    private MotorController conveyorMotor = new PWMSparkMax(3); //PWM 3

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
    private final DoubleSolenoid armSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1); //Channel 0 and 1 on pnuematic controller
    private final DoubleSolenoid bucketSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3); //Channel 0 and 1 on pnuematic controller
    private final DoubleSolenoid pusherSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5); //Channel 4 and 5 on pnuematic controller

    @Override
    public void robotInit() {
        CameraServer.startAutomaticCapture();
        leftMotor.setInverted(true);
        suckEncoder.reset();
            try {
                comp.enableDigital();
            } catch (Exception e) {
                System.out.println("Check connections");
            }
        suckEncoder.setDistancePerPulse((Math.PI * 6) / 360.0);
        conveyorEncoder.setDistancePerPulse((Math.PI * 6) / 360.0);
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
        suckEncoder.reset();
        conveyorEncoder.reset();
    }

    private boolean startButtonState = false;
    private boolean startButtonPressed = false;

    private boolean suckButtonState = false;
    private boolean suckButtonPressed = false;

    private boolean spitButtonStated = false;
    private boolean spitButtonPressed = false;

    @Override
    public void teleopPeriodic() {
        //Toggle status
        startButtonPressed = controller.getStartButtonPressed();
        suckButtonPressed = controller.getLeftStickButtonPressed();
        spitButtonPressed = controller.getRightStickButtonPressed();
        //Toggle buttons
        if(startButtonPressed){
            startButtonState = !startButtonState;
            leftMotor.setInverted(!startButtonState);
            rightMotor.setInverted(startButtonState);
        }
        if(suckButtonPressed){
            suckButtonState = !suckButtonState;        
        }
        if(spitButtonPressed){
            spitButtonStated = !spitButtonStated;
        }
        //Driving
        if(controller.getLeftBumper()){
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
        if(spitButtonStated != true){
            if(suckButtonState){
                suckMotor.set(1);
                conveyorMotor.set(1);
            }else{
                suckMotor.stopMotor();
                conveyorMotor.stopMotor();
            }
        }
        //Spitting balls🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮
            if(suckButtonState != true){
                if(suckButtonState){
                    suckMotor.set(-1);
                    conveyorMotor.set(-1);
                }else{
                    suckMotor.stopMotor();
                    conveyorMotor.stopMotor();
                }
            }
        //Pnuematics
        if(controller.getPOV() == 0){
            armSolenoid.set(DoubleSolenoid.Value.kForward);
        }else if(controller.getPOV() == 180){
            armSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(controller.getAButton()){
            bucketSolenoid.set(DoubleSolenoid.Value.kForward);
        }else if(controller.getBButton()){
            bucketSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        if(controller.getXButton()){
            pusherSolenoid.set(DoubleSolenoid.Value.kForward);
        }else if(controller.getBButton()){
            pusherSolenoid.set(DoubleSolenoid.Value.kReverse);
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
