package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import java.lang.Math;

public class DriveClass {
	public double forwardAngle = 0.0;
	public double backwardAngle = 0.0;
	public double leftAngle = 0.0;
	public double rightAngle = 0.0;
	Joystick driveyStick;
	CANTalon frontLeftMotor;
	CANTalon middleLeftMotor;
	CANTalon rearLeftMotor;
	CANTalon frontRightMotor;
	CANTalon middleRightMotor;
	CANTalon rearRightMotor;
	AHRS ahrs; 
	PinsClass pins;
	DoubleSolenoid driveSpeed;
	public RobotDrive roboDrive;
	static double negOne = -1.0;
	static double expFactor = 5.0;
	static double deadBand = 5.0;
	static double stall = 0.4;

	public DriveClass(Joystick stickZero, AHRS ahrs){
		this.ahrs = ahrs;
		this.driveyStick = stickZero;
		pins =  new PinsClass();
		driveSpeed = new DoubleSolenoid(pins.driveSolenoidA,pins.driveSolenoidB);
		frontLeftMotor = new CANTalon(pins.frontLeftMotorPin);
		middleLeftMotor = new CANTalon(pins.middleLeftMotorPin);
		rearLeftMotor = new CANTalon(pins.rearLeftMotorPin);
		frontRightMotor = new CANTalon(pins.frontRightMotorPin);
		middleRightMotor = new CANTalon(pins.middleRightMotorPin);
		rearRightMotor = new CANTalon(pins.rearRightMotorPin);
		
		roboDrive = new RobotDrive(frontLeftMotor,rearLeftMotor,frontRightMotor,rearRightMotor);
		
		middleLeftMotor.changeControlMode(TalonControlMode.Follower);
    	middleRightMotor.changeControlMode(TalonControlMode.Follower);
    	
    	middleLeftMotor.set(pins.frontLeftMotorPin); 							
    	middleRightMotor.set(pins.frontRightMotorPin);
	}
	
	
	private double calculateYAxis(){
		double yAxis;
        if (driveyStick.getY() < -0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick.getY())),expFactor)+stall)*negOne;
        }
        else if (driveyStick.getY() > 0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick.getY())),expFactor)+stall);
        }
        else{
        	yAxis = 0.0;
        }
        
        return yAxis;


	}
	
	
	private double calculateThrottleAxis(){
		double throttleAxis;
		if (driveyStick.getThrottle() < -0.1){
	       	throttleAxis = (0.6*Math.pow((Math.abs(driveyStick.getThrottle())),expFactor)+stall)*negOne;
	       }
	   else if (driveyStick.getThrottle() > 0.1){
	       	throttleAxis = (0.6*Math.pow((Math.abs(driveyStick.getThrottle())),expFactor)+stall);
	       }
	   else{
	       	throttleAxis = 0.0;
	       }
		return throttleAxis;
	   }
	
	
	private double calculateTwistAxis(){
		double twistAxis;
		if (driveyStick.getTwist() < -0.1){
	       	twistAxis = (0.6*Math.pow((Math.abs(driveyStick.getTwist())),expFactor)+stall)*negOne;
	       }
	   else if (driveyStick.getTwist() > 0.1){
	       	twistAxis = (0.6*Math.pow((Math.abs(driveyStick.getTwist())),expFactor)+stall);
	       }
	   else{
		   twistAxis = 0.0;
	       }
		return twistAxis;
	   }
	
	//Drive the Robot In Tank Drive
	public void tankDrive(){
		roboDrive.tankDrive((calculateYAxis()*-1), (calculateThrottleAxis()*-1));
	}
	
	//Drive the Robot In Arcade Drive
	public void arcadeDrive(){
		roboDrive.arcadeDrive((calculateYAxis()*-1),(calculateTwistAxis()*-1));
	}
	

	
	//Drive Forward For A Certain Amount of Time
	public void driveTime(double speed, double time){
		roboDrive.drive(speed, 0);
		Timer.delay(time);
		roboDrive.drive(0, 0);
	}
		

	//Calculate the Angle Difference
    private double calcAngle(double targetAngle, double currentAngle){
    	SmartDashboard.putNumber("Current Angle", ahrs.getAngle());
    	double diff;
    	diff = targetAngle-currentAngle;
    	if (diff > 180.0){
    		diff = -1*(360-diff);
    	}
    	else if(diff < -180.0){
    		diff = -1*(360+diff);
    	}
    	return diff;
    }
    
    //Absolute Value of A Double Value
    public static double abs(double value){
    	if (value >= 0){
    		return value;
    		}
    	else {
    		return value*-1;
    	}
    }
    
    //Calculate the Proportional Speed
    private static double proportional(double deltaAngle){
    	double speed;
    	speed = (1-stall)*(abs(deltaAngle)/180)+stall;
    	return speed;
    }
	
    //Drive To A Certain Angle
	public void driveAngle(double targetAngle){
        double angle;
        double speed;
        if(abs(calcAngle(targetAngle, ahrs.getAngle())) > deadBand){
        	angle = calcAngle(targetAngle, ahrs.getAngle());
        	if (angle<0){
        		speed = proportional(angle);
        		roboDrive.tankDrive(speed*-1, speed);
        	}
        	else{
        		speed = proportional(angle);
        		roboDrive.tankDrive(speed, speed*-1);
        	}
        
        } 
        roboDrive.tankDrive(0.0, 0.0);
        	
	}
	
	//Set a Start Angle and the Left, Right and, Backward Angles
	public void setAngles(){
		
		forwardAngle = ahrs.getAngle();
		backwardAngle = forwardAngle+180;
		if (backwardAngle >= 360 ){
			backwardAngle = backwardAngle-360;
		}
		leftAngle = forwardAngle-90;
		if (leftAngle < 0){
			leftAngle = leftAngle+360;
		}
		
		rightAngle = forwardAngle+90;
		if(rightAngle >= 360){
			rightAngle = rightAngle-360;
		}
				
	}
	
	//Reset Angle Back to Start Angle
	public void resetAngle(){
        double angle;
        double speed;
        if(abs(calcAngle(forwardAngle, ahrs.getAngle())) > deadBand){
        	angle = calcAngle(forwardAngle, ahrs.getAngle());
        	if (angle<0){
        		speed = proportional(angle);
        		roboDrive.tankDrive(speed*-1, speed);
        	}
        	else{
        		speed = proportional(angle);
        		roboDrive.tankDrive(speed, speed*-1);
        	}
        
        } 
        roboDrive.tankDrive(0.0, 0.0);
	}
	
	public void lowGear(){
		driveSpeed.set(DoubleSolenoid.Value.kForward);
	}
	
	public void highGear(){
		driveSpeed.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void passivePosition(){
		driveSpeed.set(DoubleSolenoid.Value.kOff);
	}
	
	public void drive(double speedLeft, double speedRight){
		roboDrive.tankDrive(speedLeft,speedRight);
		
	}
}

