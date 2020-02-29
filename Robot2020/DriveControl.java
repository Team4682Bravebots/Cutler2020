package frc.robot;

public class DriveControl {

    private double right_;
    private double left_;

    public DriveControl(double right, double left) {
        right_ = right;
        left_ = left;
    }

    public double getLeftPower() {
        return left_;
    }

    public double getRightPower() {
        return right_;
    }
}