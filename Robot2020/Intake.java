package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;


// TODO: We need a deploy w/ Pneumatics
// right = 2, 3
// left = 0, 1
public class Intake {
    public CANSparkMax CSM0;

    public Intake(int sht) {
        CSM0 = new CANSparkMax(sht, CANSparkMaxLowLevel.MotorType.kBrushless);
        CSM0.restoreFactoryDefaults();
    }

    public void eatit() {
        CSM0.set(.3);
    }

    public void spititout() {
        CSM0.set(-.3);
    }

    public void imfull() {
        CSM0.set(0);
    }
}