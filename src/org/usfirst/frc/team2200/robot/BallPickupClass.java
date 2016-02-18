package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class BallPickupClass {
	DoubleSolenoid intakeArms;
	Talon topRoller;
	Talon botRoller;
	PinsClass pins;
	boolean armUp;
	
	
	public BallPickupClass(){
		pins = new PinsClass();
		topRoller = new Talon(pins.topRollerPin);
		botRoller = new Talon(pins.botRollerPin);
		intakeArms = new DoubleSolenoid(pins.intakeArmSolenoidA,pins.intakeArmSolenoidB);
		
		
	}
	
	public void downPosition(){
		intakeArms.set(DoubleSolenoid.Value.kForward);

		armUp = false;
	}
	public void upPosition(){
		intakeArms.set(DoubleSolenoid.Value.kReverse);

		armUp = true;
	}
	public void passivePosition(){
		intakeArms.set(DoubleSolenoid.Value.kOff);	
	}
	public void openDoor(double speed){
		topRoller.set(speed);
		botRoller.set(speed);
	}
	
	public void intake(double speed){
		topRoller.set(speed*-1);
		botRoller.set(speed);
	}
	
	public void outake(double speed){
		topRoller.set(speed);
		botRoller.set(speed*-1);
	}
	public void shoot(double speed){
		if (armUp){ //if the arms up
			topRoller.set(speed);
			botRoller.set(speed*-1);
			
		}
		else{ //if the arms down
			topRoller.set(speed*-1);
			botRoller.set(speed);
		}
		
	}
	
	public void autoUp(){
		upPosition();
		Timer.delay(0.2);//TODO figure out time
		passivePosition();
	}
	
	public void autoShoot(){
		shoot(0.8); 
		Timer.delay(0.4); //TODO figure out time
		stop();
	}
	
	public void stop(){
		topRoller.set(0.0);
		botRoller.set(0.0);
	}

}
