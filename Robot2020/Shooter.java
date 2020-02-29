package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


// TODO: Need Feeder Pneumatics for 4 and 5
public class Shooter {
    private double kDefaultPower = 0.45;
    private static final double kOffPower = 0.0;
    private boolean shooterrunning;

    private CANSparkMax CSM1;
    private CANSparkMax CSM0;

    private double _goalVel;

    public Shooter(int port1, int port2, double goalVel) {
        CSM0 = new CANSparkMax(port1, CANSparkMaxLowLevel.MotorType.kBrushless);
        CSM0.restoreFactoryDefaults();
        CSM1 = new CANSparkMax(port2, CANSparkMaxLowLevel.MotorType.kBrushless);
        CSM1.restoreFactoryDefaults();
        //CANEncoder Enc;
        //Enc = CSM0.getEncoder();
        _goalVel = goalVel;
        
        
        stop();
    }

    public void setPower(double power) {
        CSM0.set(-power);
        CSM1.set(power);
        shooterrunning = true;

    }

    public void stop() {
        setPower(kOffPower);
        shooterrunning = false;
    }

    public void shoot() {
        setPower(kDefaultPower);
        shooterrunning = true;
        
    }

    public void setGoalVel(double goalVel){
        _goalVel = goalVel;
    }

    public double Getmotorvelocity() {
        return CSM0.getEncoder().getVelocity();
        
    }


    public void debug() {
        SmartDashboard.putBoolean("shooter running?", shooterrunning);
        SmartDashboard.putNumber("Shooter velocity", CSM0.getEncoder().getVelocity());    
        SmartDashboard.setDefaultNumber("targetPower", kDefaultPower);
        kDefaultPower = SmartDashboard.getNumber("targetPower", kDefaultPower);
    }
    public void ShootAllBalls(Carousel _Carousel, Pnuematics _Pnuematics){
            switch(_Carousel.getBallSum()){
                case 0:
                break;

                case 1: // in pos 1
                    shoot();
                    _Carousel.spinHalfSlotClockwise();
                    _Carousel.spinOneSlotClockwise();
                    //ball under turret
                    while(Getmotorvelocity() < _goalVel){}
                        if(Getmotorvelocity() >= _goalVel){
                            //trigger Pnue
                            _Pnuematics.popBall();
                            Timer.delay(0.1); //TODO CHECK TIME!!!!
                            _Pnuematics.retractPapa();

                        }
                    stop();
                break;

                case 2:
                    shoot();
                    _Carousel.spinHalfSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }

                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();

                    }
                    stop();
                break;

                case 3:
                    shoot();
                    _Carousel.spinHalfSlotCounterClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }

                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();

                    }

                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();

                    }

                    stop();
                break;
                case 4:
                    shoot();
                    _Carousel.spinHalfSlotCounterClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotCounterClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotCounterClockwise();
                    _Carousel.spinOneSlotCounterClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotCounterClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    stop();
                break;
                case 5:
                    _Carousel.spinHalfSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    _Carousel.spinOneSlotClockwise();
                    while(Getmotorvelocity() < _goalVel){}
                    if(Getmotorvelocity() >= _goalVel){
                        //trigger Pnue
                        _Pnuematics.popBall();
                        Timer.delay(0.1); //TODO CHECK TIME!!!!
                        _Pnuematics.retractPapa();
                    }
                    stop();
                break;



            } 
            _Carousel.reset();
        }







}