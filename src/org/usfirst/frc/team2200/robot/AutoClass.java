package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Timer;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

//test comment 
// test 2

public class AutoClass {
	DriveClass drive;
	BallPickupClass ballPickup;
	public static DigitalInput leftSensor;
	public static DigitalInput rightSensor;
	PinsClass pins;
	DigitalInput ultraInput;
	DigitalOutput ultraOutput;

	public AutoClass(DriveClass drive, BallPickupClass ballPickup){
		this.drive = drive;
		this.ballPickup = ballPickup;
		pins = new PinsClass();
		leftSensor = new DigitalInput(pins.leftLineSensor);
		rightSensor = new DigitalInput(pins.rightLineSensor);
		ultraInput = new DigitalInput(8);
		ultraOutput = new DigitalOutput(9);
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
		//drive.driveAngle(90);
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot(); 
	}
	
	//Ramparts Autonomous
	
	public void rampartsOne(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rampartsTwo(){
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90-drive.forwardAngle);
		drive.driveStraight(1.3,90-drive.forwardAngle);
		drive.driveAngle(0-drive.forwardAngle);	
		drive.driveStraight(2.3, 0-drive.forwardAngle); //TODO calculate distance
        drive.driveAngle(60-drive.forwardAngle); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60-drive.forwardAngle); //TODO calculate distance
        ballPickup.autoShoot();		
	}
	
	public void rampartsThree(){
		drive.driveTime(0.7, 2.5);
		
	}
	
	public void rampartsFour(){
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90);
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot(); 		
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
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90);
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot();	
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
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90);
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot();		
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
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90);
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot();		
	}
	
	public void testUltra() {
		
		boolean inputFound = false;
		double previousTime = Timer.getFPGATimestamp();
		ultraInput.requestInterrupts();
		ultraInput.setUpSourceEdge(true, false);
		double ultraTime = 0.0;
		double timeRead = 0.0;
		ultraOutput.set(true);
		Timer.delay(0.01);
		ultraOutput.set(false);
		
		//while (!inputFound) {
			
			timeRead = ultraInput.readRisingTimestamp();
			
			if (timeRead > 0.0) {
				
				inputFound = true;
			}
			
		//}
		
		ultraTime = timeRead - previousTime;
		
		SmartDashboard.putNumber("Ultra time", ultraTime);
		SmartDashboard.putNumber("Time read", timeRead);
		SmartDashboard.putNumber("Previous time", previousTime);
		
	}
}
