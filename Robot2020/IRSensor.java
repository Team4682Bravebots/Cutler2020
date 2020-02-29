package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class IRSensor{

    private DigitalInput ai;

    public IRSensor(int ir_port)
    {
        ai = new DigitalInput(0);
       
    }
    
        
    public Boolean getIRValue() {
        SmartDashboard.putBoolean("ai",ai.get() );
        return ai.get();
      
        
    }
}