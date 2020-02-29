package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive {

    private TalonFX rightMaster_;
    private TalonFX leftMaster_;
    private TalonFX rightSlave_;
    private TalonFX leftSlave_;

    public ArcadeDrive(int leftM, int rightM, int rightS, int leftS) {
        leftMaster_ = new TalonFX(leftM);
        rightMaster_ = new TalonFX(rightM);
        rightSlave_ = new TalonFX(rightS);
        leftSlave_ = new TalonFX(leftS);

        leftMaster_.configFactoryDefault();
        rightMaster_.configFactoryDefault();
        leftSlave_.configFactoryDefault();
        rightSlave_.configFactoryDefault();

        leftSlave_.follow(leftMaster_);
        rightSlave_.follow(rightMaster_);
    }

    public void drive(double driveX, double driveY, double look) {
        DriveControl controller = calculatePower(driveX, driveY, look);
        leftMaster_.set(ControlMode.PercentOutput, controller.getLeftPower());
        rightMaster_.set(ControlMode.PercentOutput, controller.getRightPower());
    }
    public void directDrive(double leftSide, double rightSide){
        leftMaster_.set(ControlMode.PercentOutput,leftSide);
        rightMaster_.set(ControlMode.PercentOutput,rightSide);
    }

    private DriveControl calculatePower(double driveX, double driveY, double look) {
        driveX = Utils.applyDeadband(Utils.limit(driveX));
        driveY = Utils.applyDeadband(Utils.limit(driveY));

        //TODO: This is probably wrong...
        double right = driveX + driveY + look;
        double left = driveX - driveY - look;
        
        return new DriveControl(right, left);
    }

    public void debug() {
        debugTalon("Right Drive Motor", rightMaster_);
        debugTalon("Left Drive Motor", leftMaster_);
    }

    private void debugTalon(String key, TalonFX talon) {
        SmartDashboard.putNumber(key + " MC Actual", talon.getSelectedSensorVelocity());
        SmartDashboard.putNumber(key + " MC Error", talon.getClosedLoopError());
    }
}