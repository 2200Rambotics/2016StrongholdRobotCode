package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
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
	Ultrasonic ultraLeft;
	Ultrasonic ultraRight;

	public AutoClass(DriveClass drive, BallPickupClass ballPickup){
		this.drive = drive;
		this.ballPickup = ballPickup;
		pins = new PinsClass();
		leftSensor = new DigitalInput(pins.leftLineSensor);
		rightSensor = new DigitalInput(pins.rightLineSensor);
		ultraLeft = new Ultrasonic(pins.ultraLeftInput, pins.ultraLeftOutput);
		ultraRight = new Ultrasonic(pins.ultraRightInput, pins.ultraRightOutput);

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
		drive.driveAngle(180);
		drive.driveTime(-0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveStraightCompass(0.8, 1.6); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        drive.driveStraightCompass(0.6, 1.2); //TODO calculate distance
        ballPickup.autoShoot(); 
	}
	
	//Ramparts Autonomous
	
	public void rampartsOne(){
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		
	}
	
	public void rampartsTwo(){
		drive.setAngles();
		drive.driveTime(0.7, 2.5);
		drive.resetAngle();
		lineUp();
		drive.driveAngle(90-drive.forwardAngle);
		drive.driveStraightCompass(1.3,90-drive.forwardAngle);
		drive.driveAngle(0-drive.forwardAngle);	
		drive.driveStraightCompass(2.3, 0-drive.forwardAngle); //TODO calculate distance
        drive.driveAngle(60-drive.forwardAngle); //

        ballPickup.autoUp();
        drive.driveStraightCompass(1.2, 60-drive.forwardAngle); //TODO calculate distance
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
		/*
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot(); 	
        */	
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
		/*
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot();	
        */
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
		/*
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot();		
        */
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
		/*
		drive.driveStraight(1.3,90);
		drive.driveAngle(0);	
		drive.driveStraight(2.3, 0); //TODO calculate distance
        drive.driveAngle(60); //TODO calculate angle
        ballPickup.autoUp();
        drive.driveStraight(1.2, 60); //TODO calculate distance
        ballPickup.autoShoot();		
        */
	}
	
	public double getLeftUltra() {
		
		ultraLeft.setAutomaticMode(true);
		
		double range = ultraLeft.getRangeMM();
		range = range /1000;
		
		SmartDashboard.putNumber("ultra range", range);
		
		return range;
				
		
	}
	
	public double getRightUltra() {
		
		ultraRight.setAutomaticMode(true);
		
		double range = ultraRight.getRangeMM();
		range = range /1000;
		
		SmartDashboard.putNumber("ultra range (metres)", range);
		
		return range;
				
		
	}
}
