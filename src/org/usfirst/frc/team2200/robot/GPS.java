package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import com.kauailabs.navx.frc.*;

public class GPS {
	
	Encoder leftEnc;
	Encoder rightEnc;
	AHRS ahrs;
	DriveClass driveclass;
	DigitalInput leftSensor;
	DigitalInput rightSensor;

	
	public GPS(Encoder leftEnc, Encoder rightEnc, AHRS ahrsGPS, DigitalInput leftSensor, DigitalInput rightSensor ){
		 
		this.leftEnc = leftEnc;
		this.rightEnc = rightEnc;
		this.ahrs = ahrsGPS;
		this.leftSensor = leftSensor;
		this.rightSensor = rightSensor;
		
	}

	double xPosition = 0.0;
	double yPosition = 0.0;
	double forwardAngle = 0.0;
	
	void setPosition(double x, double y){
		
		xPosition = x;
		yPosition = y;
		
		leftEnc.reset();
		rightEnc.reset();
		
		forwardAngle = ahrs.getCompassHeading();
		
	}
	
	void updatePosition() {
		
		double d = 0.0;
		double currentAngle = 0.0;
		
		d = driveclass.calcEncoderDistance();
		
		currentAngle = ahrs.getCompassHeading();
		
		xPosition = xPosition + (d)*Math.cos(currentAngle-forwardAngle);
		yPosition = yPosition + (d)*Math.sin(currentAngle-forwardAngle);
		
		//return currentPosX or currentPosY here??
		//field is 7.90m x 16.5m and tape is 3.36m away from wall
		
		if (yPosition > 13.0 && yPosition < 14.0 && (Robot.getLeftLineSensor() || Robot.geRightLineSensor())) {
			
			yPosition = 13.5; //TODO get correct numbers
			
		}
		
		if (yPosition > 3.0 && yPosition < 4.0 && (Robot.getLeftLineSensor() || Robot.geRightLineSensor())) {
			
			yPosition = 3.5; //TODO get correct numbers
			
		}
		
	}
		
	double getXPosition(){
		
		return xPosition;
		
	}
	
	double getYPosition(){
		
		return yPosition;
		
	}
	
	
	void goToLocation(double x, double y){
		
		double targetDirection;
		double d = 999.0;
		
		targetDirection = Math.atan((y-yPosition)/(x-xPosition));
		targetDirection = (targetDirection/(2*Math.PI))*360;
		
		this.updatePosition();
		d = Math.pow(x-xPosition, 2) + Math.pow(y-yPosition, 2);
		d = Math.pow(d, 0.5);
		
		driveclass.driveStraight(d, targetDirection+forwardAngle);

		
	}
	
}
