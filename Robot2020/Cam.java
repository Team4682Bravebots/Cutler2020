package frc.robot;

import edu.wpi.first.networktables.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Cam {

    private static final double kP = 0.01f;
    private static final double kMinPower = 0.1;
    private static final double kErrorThreshold = 3.0;

    private Turret controlTurret_;
    private Shooter shooty_;
    private boolean isAiming_;
    private boolean targetFound_;
    private NetworkTable networkTable_;
    //private double xError_;
    //private double yError_;

    public Cam(Turret turret, Shooter shooty) {
        controlTurret_ = turret;
        isAiming_ = false;
        targetFound_ = false;
        shooty_ = shooty;
        //xError_ = 0;
        //yError_ = 0;

    }

    public void update() {
        if (isAiming_) {
            aim();
            controlTurret_.update();
        }

        if (targetFound_) {
            shooty_.shoot();
        }
    }

    public void startAiming() {
        isAiming_ = true;
    }

    public boolean isTargetFound() {
        return targetFound_;
    }

    public void debug() {
        //SmartDashboard.putNumber("X Error", xError_);
        //SmartDashboard.putNumber("Y Error", yError_);
        SmartDashboard.putBoolean("Is Cam Aiming?", isAiming_);
        SmartDashboard.putBoolean("Has Cam Found Target?", targetFound_);
        //SmartDashboard.putNumber("moveme", 0);
    }

    private void aim() {
       // networkTable_ = ;
        // get error
        double xError_ = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double tv_ = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double yError_ = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        SmartDashboard.putNumber("X Error", xError_);
        SmartDashboard.putNumber("Y Error", yError_);
        SmartDashboard.putNumber("tv", tv_);
        double adjust = 0.0f;
        if (tv_ >= 1.0) {
            if (xError_ > kErrorThreshold) {
                adjust = kP * xError_ - kMinPower;

            } else if (xError_ < -kErrorThreshold) {
                adjust = kP * xError_ + kMinPower;
            }
            SmartDashboard.putNumber("moveme", adjust);
            if (adjust != 0.0) {
                controlTurret_.setPower(adjust);
            } else {
                setTargetFound();
            }
        }
    }

    private void setTargetFound() {
        controlTurret_.turnOff();
        targetFound_ = true;
        isAiming_ = false;
    }
}