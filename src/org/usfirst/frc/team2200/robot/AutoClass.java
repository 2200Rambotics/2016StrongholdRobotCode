package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Timer;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

//test comment 
// test 2

public class AutoClass {
	DriveClass drive;
	BallPickupClass ballPickup;
	public static DigitalInput leftSensor;
	public static DigitalInput rightSensor;
	PinsClass pins;

	public AutoClass(DriveClass drive, BallPickupClass ballPickup){
		this.drive = drive;
		this.ballPickup = ballPickup;
		pins = new PinsClass();
		leftSensor = new DigitalInput(pins.leftLineSensor);
		rightSensor = new DigitalInput(pins.rightLineSensor);
	}
	
	//Low Goal Autonomous
	private void lineUp(){
    	while (!leftSensor.get() || !rightSensor.get()){
        	
    		if (!leftSensor.get() && !rightSensor.get()){
    			drive.drive(0.45, 0.45);
    		}
    		
    		else if (!leftSensor.get() && rightSensor.get()){
    			drive.drive(-0.45,-0.45);
    			Timer.delay(0.05);
    			drive.drive(0.45, -0.45);
    			Timer.delay(0.1);
    		}
    		
    		else if (leftSensor.get() && !rightSensor.get()){
    			drive.drive(-0.45,-0.45);
    			Timer.delay(0.05);
    			drive.drive(-0.45, 0.45);
    			Timer.delay(0.1);
    		}	
    	}

		drive.drive(0, 0);	
	
	}
	
	public void lowGoal(){
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90);
		drive.driveStraight(4, 90); //calculate distance
        drive.driveAngle(36); //calculate angle
        ballPickup.autoUp(); //calculate time
        drive.driveStraight(2.3, 36); //calculate distance
        ballPickup.autoShoot(); //calculate time
	}
	
	//Ramparts Autonomous
	
	public void rampartsOne(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rampartsTwo(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rampartsThree(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rampartsFour(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	//Moat Autonomous
	
	public void moatOne(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void moatTwo(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void moatThree(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void moatFour(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	//Rough Terrain Autonomous
	
	public void roughTerrainOne(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void roughTerrainTwo(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void roughTerrainThree(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void roughTerrainFour(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	//Rock Wall Autonomous
	
	public void rockWallOne(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rockWallTwo(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rockWallThree(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rockWallFour(){
		drive.driveTime(0.7, 2.5);
		
	}
	
}
