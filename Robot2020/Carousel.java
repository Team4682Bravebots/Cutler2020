/*
.______   .______          ___   ____    ____  _______ .______     ______   .___________.    _______.
|   _  \  |   _  \        /   \  \   \  /   / |   ____||   _  \   /  __  \  |           |   /       |
|  |_)  | |  |_)  |      /  ^  \  \   \/   /  |  |__   |  |_)  | |  |  |  | `---|  |----`  |   (----`
|   _  <  |      /      /  /_\  \  \      /   |   __|  |   _  <  |  |  |  |     |  |        \   \    
|  |_)  | |  |\  \----./  _____  \  \    /    |  |____ |  |_)  | |  `--'  |     |  |    .----)   |   
|______/  | _| `._____/__/     \__\  \__/     |_______||______/   \______/      |__|    |_______/    
                                                                                                     
*/

package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.*;

public class Carousel
{ 

    private Position m_carousel;
    private int globalPosition; // either loading or shooting
    private int isPositionLoaded;
    private double k_rotationposition;
    private IRSensor[] irsensors;
    private int[] _ballslots;
    public boolean _full;
    private int _ballSum;

    //        FRONT/INTAKE
    //          |
    //          |
    //         \/
    //    ____________
    //   /      0     \
    //  /              \
    // /    4       1   \
    // \                /
    //  \    3     2   /
    //   \____________/




    public Carousel(Position c, IRSensor[] ir)
    {
        m_carousel = c;
        m_carousel.initializePositionalPID();
        k_rotationposition = 4.7123;
        irsensors[0] = new IRSensor(0);
        irsensors[1] = new IRSensor(1);
        irsensors[2] = new IRSensor(2);
        irsensors[3] = new IRSensor(3);
        irsensors[4] = new IRSensor(4);
        _full = false;
        globalPosition = 0;
    }
    public boolean getSlot0(){
        return irsensors[0].getIRValue();
    }
    public boolean getSlot1(){
        return irsensors[1].getIRValue();
    }
    public boolean getSlot2(){
        return irsensors[2].getIRValue();
    }
    public boolean getSlot3(){
        return irsensors[3].getIRValue();
    }
    public boolean getSlot4(){
        return irsensors[4].getIRValue();
    }
    private int toggle(){
        if(globalPosition == 0){
            globalPosition = 1;
            return 1;
        }else if(globalPosition == 1){
            globalPosition = 0;
            return 0;
        }else{
            return globalPosition;
        }
    }
    public int getBallSum(){
        return _ballSum;
    }

    public void spinHalfSlotClockwise(){
        m_carousel.runCarousel(k_rotationposition/2);
        toggle();

    }
    public void spinHalfSlotCounterClockwise(){
        m_carousel.runCarousel(-k_rotationposition/2);
        toggle();
    }

    public void spinOneSlotClockwise()
    {
        m_carousel.runCarousel(k_rotationposition);
    }
    public void spinOneSlotCounterClockwise(){
        m_carousel.runCarousel(-k_rotationposition);
    }
    /*
    public void spinFullRotation()
    {
        m_carousel.runCarousel(k_rotationposition / 5);
    }
    
    public int getBallsLoaded()
    {
        return k_BallsLoaded;
    }
    */
    public void LoadCarryChecksum(){
        // moves balls accordingly to if their is one in the first slot
        // should be run evey time we intake a ball or periodically
        if(globalPosition == 0){
            if( 
            getSlot0() && 
            getSlot1() &&
            getSlot2() &&
            getSlot3() &&
            getSlot4()
            ){
                //DONT ACCEPT BALLS
                SmartDashboard.putBoolean("FULL!", true);
                _full = true;
                Timer.delay(.2);//TODO TEST TIMES!!!!!!
                spinHalfSlotClockwise();
            }

            else if(getSlot0()){
                Timer.delay(0.3);
                spinOneSlotClockwise();
                _ballSum++;
            }
        }
    }

    /*
    public void updateSlotState(){
        if(irsensors)




        /*
        if(irsensors[0].getIRValue() == true && IRS_2.getIRValue() == true)
        {
            System.out.println("This Slot is Full");
            isPositionLoaded = 1;
        }
        
        if(IRS_1.getIRValue() == false && IRS_2.getIRValue() == false)
        {
            System.out.println("This Slot is Empty, FEED ME DADDY");
            isPositionLoaded = 0;
        }
        
    }
    */
    public void displayToSD(){
        SmartDashboard.putBoolean("Slot_0", getSlot0());
        SmartDashboard.putBoolean("Slot_1", getSlot1());
        SmartDashboard.putBoolean("Slot_2", getSlot2());
        SmartDashboard.putBoolean("Slot_3", getSlot3());
        SmartDashboard.putBoolean("Slot_4", getSlot4());
    }
    /*
    public void loadSlot(int slot)
    {
        switch(isPositionLoaded)
        {
            case 1: 
            spinOneSlot();
            break;

            case 2:
            spinFullRotation();
            break;
        }
    }
    */
    public void reset(){
        spinHalfSlotClockwise();
        _ballSum = 0;
    }


}