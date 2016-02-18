package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;

import com.kauailabs.navx.frc.*;

public class GPS {
	
	Encoder leftEnc;
	Encoder rightEnc;
	AHRS ahrs;
	DriveClass driveclass;
	AutoClass auto;
	DigitalInput leftSensor;
	DigitalInput rightSensor;
	Ultrasonic ultraLeft;
	Ultrasonic ultraRight;

	
	public GPS(Encoder leftEnc, Encoder rightEnc, AHRS ahrsGPS, DigitalInput leftSensor, DigitalInput rightSensor, Ultrasonic ultraLeft, Ultrasonic ultraRight ){
		 
		this.leftEnc = leftEnc;
		this.rightEnc = rightEnc;
		this.ahrs = ahrsGPS;
		this.leftSensor = leftSensor;
		this.rightSensor = rightSensor;
		this.ultraLeft = ultraLeft;
		this.ultraRight = ultraRight;
		
	}

	double xPosition = 0.0;
	double yPosition = 0.0;
	double forwardAngle = 0.0;
	
	void setPosition(double x, double y){
		
		xPosition = x;
		yPosition = y;
		
		leftEnc.reset();
		rightEnc.reset();
		
		forwardAngle = ahrs.getAngle();
		
	}
	
	void updatePosition() {
		
		double d = 0.0;
		double currentAngle = 0.0;
		
		d = driveclass.calcEncoderDistance();
		
		currentAngle = ahrs.getAngle();
		
		xPosition = xPosition + (d)*Math.cos(currentAngle-forwardAngle);
		yPosition = yPosition + (d)*Math.sin(currentAngle-forwardAngle);
		
		//return currentPosX or currentPosY here??
		//field is 7.90m x 16.5m and tape is 3.36m away from wall
		
		if (yPosition > 13.0 && yPosition < 14.0 && (Robot.getLeftLineSensor() || Robot.geRightLineSensor())) {
			
			yPosition = 13.5; //TODO get correct numbers
			
		}
		
		else if (yPosition > 3.0 && yPosition < 4.0 && (Robot.getLeftLineSensor() || Robot.geRightLineSensor())) {
			
			yPosition = 3.5; //TODO get correct numbers
			
		}
		
		//change x position with ultra input
		if (auto.getLeftUltra() < auto.getRightUltra()){
			
			xPosition = auto.getLeftUltra();
		
		}
		
		else  {
			
			xPosition = 7.92 - (auto.getRightUltra()); //total field width subtract ultra reading
			
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
		
		driveclass.driveStraightCompass(d, targetDirection+forwardAngle);

		
	}
	
}
