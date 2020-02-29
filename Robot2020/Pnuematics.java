package frc.robot;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pnuematics {

    private DoubleSolenoid intake_;
    private DoubleSolenoid popper_;
    private DoubleSolenoid fangs_;
    private Solenoid popUp_;
    private Solenoid retractPopper_;
    private boolean ballpusherout;
    private boolean intaker;
    private boolean feeder;
    private static int kSolChannel_deploy = 2; // deploy intake
    private static int kSolChannel_deploy2 = 3; // deploy intake
    private static int kSolChannel_deploy3= 4; // popper
    private static int kSolChannel_deploy4 =5; // popper
    private static int kSolChannel_deploy5 =6; // fangy bois
    private static int kSolChannel_deploy6 =7; // fangy bois
    
    public Pnuematics(int pcmId_) {
        intake_ = new DoubleSolenoid(pcmId_, kSolChannel_deploy, kSolChannel_deploy2);
        popper_ = new DoubleSolenoid(pcmId_, kSolChannel_deploy3, kSolChannel_deploy4);
        fangs_ = new DoubleSolenoid(pcmId_, kSolChannel_deploy5, kSolChannel_deploy6);
        popUp_ = new Solenoid(4);
        retractPopper_ = new Solenoid(5);
        intaker = false;
    }

    public void pushballout() {
        popper_.set(Value.kForward);
        ballpusherout = true;
    }

    public void bringballin() {
        intake_.set(Value.kReverse);
        ballpusherout = false;
    }

    public void bringballin2(){
        fangs_.set(Value.kReverse);
        feeder=true;
    }

    public void closemouth(){
        fangs_.set(Value.kReverse);
        feeder = false;
        
    }

 

    public void senddeployout(){
        popper_.set(Value.kForward);
        intaker = true;
    }

    public void debug(){
        SmartDashboard.putBoolean("ball pusher out?", ballpusherout);
        SmartDashboard.putBoolean("intake out?", intaker);
        SmartDashboard.putBoolean("feeder out?", feeder);
    }
    //CUTLER'S SHOOTER CODE
    public void popBall(){
        retractPopper_.set(false);
        popUp_.set(true);
    }
    public void retractPapa(){
        popUp_.set(false);
        retractPopper_.set(true);
    }









}