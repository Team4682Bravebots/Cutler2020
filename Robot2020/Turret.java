package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Turret {

    private static final double kPowerOff = 0.0;
    private static final double kDefaultPower = 0.1;
    private static final double kEncoderTicksPerRevolution = 118;
    private static final double kAngleThreshold = 270;

    private CANSparkMax CSM0;
    private CANEncoder encoder_;
    private boolean isOn_;
   
    // private double boop;
    // private double jax;
    // private double cfa;
    // private double cfb;
    // private double cfc;
    // private boolean hbbbp;
    // private double intval;
    // private final double timeset = 0.0200;
    // public final double spd = 0.1;

    public Turret(int vrm) {
        CSM0 = new CANSparkMax(vrm, CANSparkMaxLowLevel.MotorType.kBrushless);
        CSM0.restoreFactoryDefaults();
        encoder_ = CSM0.getEncoder();
        encoder_.setPosition(0);
        isOn_ = false;
        
    }

    public void update() {
        if (isOn_) {
            if (Math.abs(getEncoderAngle_degrees()) >= kAngleThreshold) {
                turnOff();
            }
        }
    }

    public void debug() {
        SmartDashboard.putNumber("Turret Current Angle (Degrees)", getEncoderAngle_degrees());
        SmartDashboard.putBoolean("Is Turret On?", isOn_);
    }

    public void setPower(double power) {
        CSM0.set(power);
    }

    public void turnOn() {
        setPower(kDefaultPower);
        isOn_ = true;
    }

    public void turnOff() {
        setPower(kPowerOff);
        isOn_ = false;
    }

    public void reset() {
        CSM0.restoreFactoryDefaults();
    }

    public double getEncoderAngle_degrees() {
        return encoder_.getPosition() / 360.0 * kEncoderTicksPerRevolution;
    }

    public boolean getState(){
        return isOn_;
    }
    

    }
    // public void test() {
    //     // CSM0.set(joy1.getRawAxis(1)*1);
    //     // CSM1.set(joy1.getRawAxis(1)*-1);

    //     intval = cfb / 360 * enc; // *5 *9.05;
    //     // if (joy1.getRawButton(1)) {

    //     // CSM0.set(spd);
    //     // } else {

    //     // CSM0.set(0);
    //     // }
    //     // if (joy1.getRawButton(3)) {
    //     // hbbbp = true;
    //     // }
    //     if (hbbbp && intval >= 1) {
    //         CSM0.set(spd);

    //         boop = CSM0.getEncoder().getPosition();

    //         if (boop >= intval) {
    //             CSM0.set(0);
    //             hbbbp = false;
    //         }
    //     }

    //     if (hbbbp && intval <= 0) {
    //         CSM0.set(-spd);

    //         boop = CSM0.getEncoder().getPosition();

    //         if (boop <= intval) {
    //             CSM0.set(0);
    //             hbbbp = false;
    //         }

    //     }

    // }

    // public double getcfb() {
    //     return cfb;
    // }

    // public double getboop() {
    //     return boop;
    // }

    // public double getintval() {
    //     return intval;
    // }

    // public void ChangeTurretAngle(double new_cfa, double new_cfc) {
    //     boop = jax;
    //     cfa = new_cfa;// joy1.getRawAxis(3);
    //     cfc = new_cfc; // joy1.getRawAxis(2);
    //     if (cfa >= 1) {
    //         cfb = jax + 0.5;
    //         jax += 0.5;
    //         // cfb += 0.5;
    //     } else if (cfc >= 1) {
    //         cfb = jax + -0.5;
    //         jax += -0.5;
    //     }
    // }

    // public void setbbutton(boolean ibbt) {

    //     hbbbp = ibbt;
    // }

    // public void emergencyreset() {
    //     CSM0.getEncoder().setPosition(0);
    //     hbbbp = false;
    //     cfb = 0;
    //     jax = 0;
    //     intval = 0;
    //     boop = 0;
    // }
