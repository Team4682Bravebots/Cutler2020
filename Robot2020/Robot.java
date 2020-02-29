/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*
.______   .______          ___   ____    ____  _______ .______     ______   .___________.    _______.
|   _  \  |   _  \        /   \  \   \  /   / |   ____||   _  \   /  __  \  |           |   /       |
|  |_)  | |  |_)  |      /  ^  \  \   \/   /  |  |__   |  |_)  | |  |  |  | `---|  |----`  |   (----`
|   _  <  |      /      /  /_\  \  \      /   |   __|  |   _  <  |  |  |  |     |  |        \   \    
|  |_)  | |  |\  \----./  _____  \  \    /    |  |____ |  |_)  | |  `--'  |     |  |    .----)   |   
|______/  | _| `._____/__/     \__\  \__/     |_______||______/   \______/      |__|    |_______/    
                                        The Team Strikes Back!
                                                                                                     
*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;

import java.sql.Driver;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // joystick constants \\
    private static final int kAButton = 1;
    private static final int kBButton = 2;
    private static final int kXButton = 3;
    private static final int kYButton = 4;
    private static final int kLeftTopButton = 5;
    private static final int kRightTopButton = 6;
    private static final int kStartButton = 7;
    private static final int kSelectButton = 8;
    private static final int kLeftJoytstickButton = 9;
    private static final int kRightJoystickButton = 10;

    private static final int kLeftJoystickAxis_x = 0;
    private static final int kLeftJoystickAxis_y = 1;
    private static final int kLeftTriggerAxis = 2;
    private static final int kRightTriggerAxis = 3;
    private static final int kRightJoystickAxis_x = 4;
    private static final int kRightJoystickAxis_y = 5;
    private static final double kTriggerThreshold = 0.3;

    private static final int kDPad_up = 0;
    private static final int kDPad_left = 90;
    private static final int kDPad_down = 180;
    private static final int kDPad_right = 270;

    // debug setting \\
    private static final boolean kDebug = true;

    // driver ports \\
    private static final int kDriverPort = 0;
    private static final int kCoDriverPort = 1;

    // component ports \\
    private static final int kTurretSparkPort = 1;
    private static final int kShooterSparkPort1 = 3;
    private static final int kShooterSparkPort2 = 2;
    Compressor c;
    // TODO: Configure these
    private static final int kIntakeSparkPort = 4;
    private static final int kCarouselSparkPort = 5;
    private static final int kDriveLeftMasterPort = 6;
    private static final int kDriveRightMasterPort = 7;
    private static final int kDriveLeftSlavePort = 8;
    private static final int kDriveRightSlavePort = 9;

    private static final int pcmId_ = 10;

    // Carousel motor
    CANSparkMax _mcarousel = new CANSparkMax(kCarouselSparkPort, MotorType.kBrushless);
    Position p = new Position(_mcarousel);

    // IRSensor ir1 = new IRSensor(0);
    // IRSensor ir2 = new IRSensor(1);

    // components \\
    private Joystick driver_;
    private Joystick coDriver_;
    private Turret turret_;
    private Cam camera_;
    private Shooter shooty_;
    private Intake intake_;
    private ArcadeDrive drive_;
    private Carousel carousel_;
    private Pnuematics Pnuematics_;
    private IRSensor[] irsensors_;

    // auto crap \\
    private double VelGol;

    /**
     *
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        c = new Compressor(pcmId_);
        driver_ = new Joystick(kDriverPort);
        coDriver_ = new Joystick(kCoDriverPort);
        turret_ = new Turret(kTurretSparkPort);
        shooty_ = new Shooter(kShooterSparkPort1, kShooterSparkPort2, VelGol);
        camera_ = new Cam(turret_, shooty_);
        intake_ = new Intake(kIntakeSparkPort);
        carousel_ = new Carousel(p, irsensors_);
        drive_ = new ArcadeDrive(kDriveLeftMasterPort, kDriveRightMasterPort, kDriveLeftSlavePort,
                kDriveRightSlavePort);
        Pnuematics_ = new Pnuematics(pcmId_);
        c.setClosedLoopControl(true);
        c.start();
        VelGol = 7000;
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        if (kDebug) {
            turret_.debug();
            camera_.debug();
            shooty_.debug();
        }
        SmartDashboard.putNumber("SetShooterVel", VelGol);
        SmartDashboard.putNumber("NumOfBall", carousel_.getBallSum());
        SmartDashboard.putBoolean("IsTurretEnabled", turret_.getState());
        SmartDashboard.putNumber("Shooter_Vel", shooty_.Getmotorvelocity());
        //SmartDashboard.putNumber("Pos of caroslool", value)
        SmartDashboard.putNumber("Pos of Shotput", turret_.getEncoderAngle_degrees());

        
    }

    @Override
    public void autonomousInit() {
        
    }
    @Override
    public void autonomousPeriodic() {
        
        //DONE: drive, camera, confirm, ballCount, 1/2turn, repet(ballCount)times[servo, turn]
        drive_.directDrive(0.1, 0.1);
        Timer.delay(1);// in seconds
        camera_.startAiming();
        camera_.update();
        SmartDashboard.putBoolean("targetFoundInAuto", camera_.isTargetFound());
        shooty_.ShootAllBalls(carousel_, Pnuematics_);
        if(Timer.getMatchTime() < 15){
            Timer.delay(15-Timer.getMatchTime());
        }
    }

    
        

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        // c.setClosedLoopControl(true);
        // DRIVER CODE \\

        // CO-DRIVER CODE \\
        // A Button = turn on Camera and aim
        if (coDriver_.getRawButtonPressed(kBButton)) {
            camera_.startAiming();
        }
        // TODO:change back to co driver
        if (coDriver_.getRawButtonPressed(kRightTopButton)) {
            shooty_.shoot();
        }

        if (coDriver_.getRawButtonPressed(kLeftTopButton)) {
            shooty_.stop();
        }

        if (coDriver_.getRawButtonPressed(kStartButton)) {
            intake_.eatit();

        }

        if (coDriver_.getRawButtonPressed(kSelectButton)) {
            Pnuematics_.senddeployout();

        }

        if (coDriver_.getRawButtonPressed(kXButton)) {
            carousel_.spinOneSlotClockwise();
        }

        if (coDriver_.getRawButtonPressed(kYButton)) {
            Pnuematics_.pushballout();
        }

        if (coDriver_.getRawButtonPressed(kAButton)) {
            Pnuematics_.bringballin();
        }

        if (coDriver_.getRawButtonPressed(kDPad_up)) {
            Pnuematics_.bringballin2();
        }
        if (coDriver_.getRawButtonPressed(kDPad_down)) {
            Pnuematics_.closemouth();
        }
        if(driver_.getRawButton(1)){
            //TODO:TEST THIS BIG SCARY CODE
            shooty_.ShootAllBalls(carousel_, Pnuematics_);
        }else{

        }

        drive_.drive(-driver_.getRawAxis(kRightJoystickAxis_x), driver_.getRawAxis(kRightJoystickAxis_y),
                driver_.getRawAxis(kLeftJoystickAxis_x));

        camera_.update();

        //TODO:TEST THIS BIG SCARY CODE
        carousel_.LoadCarryChecksum();
    }
} // button to stop shooting
  // button to aim
  // button to deploy intake button to spin intake
  // button carousel
  // pop ball
