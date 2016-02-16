package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TelescopingArmClass {
	
	DoubleSolenoid armDeployed;
	Talon teleArm;
	PinsClass pins;
	boolean armUp;

	public TelescopingArmClass(){
		pins = new PinsClass();
		teleArm = new Talon(pins.teleArmMotor);
		armDeployed = new DoubleSolenoid(pins.teleArmSolenoidA,pins.teleArmSolenoidB);
		
	}
	
	public void unlockedAndUp(){
		armDeployed.set(DoubleSolenoid.Value.kForward);
		armUp = true;
	}
	
	public void locked(){
		armDeployed.set(DoubleSolenoid.Value.kReverse);
		armUp = false;
		
	}
	
	public void passivePosition(){
		armDeployed.set(DoubleSolenoid.Value.kOff);
		
	}
	
	public void extend(double speed){
		if (armUp){
			teleArm.set(speed*-1);
		}
		else{
			SmartDashboard.putString("WARNING:", "Deploy Arm Before Attempting to Extend");
		}
	}
	
	public void stop(){
		teleArm.set(0);
	}
	
	public void halfSpeed(){
		if (armUp){
			teleArm.set(.2);
		}
		else{
			SmartDashboard.putString("WARNING:", "Deploy Arm Before Attempting to Retract");
		}
	}
	public void retract(double speed){
		if (armUp){
			teleArm.set(speed);
		}
		else{
			SmartDashboard.putString("WARNING:", "Deploy Arm Before Attempting to Retract");
		}
	}

	


	


}
