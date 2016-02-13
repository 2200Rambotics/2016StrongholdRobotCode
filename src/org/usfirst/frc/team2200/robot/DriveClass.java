package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;


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
	Encoder encLeft;
	Encoder encRight;
	public RobotDrive roboDrive;
	static double negOne = -1.0;
	static double expFactor = 5.0;
	static double deadBand = 5.0;
	static double stall = 0.4;

	public DriveClass(Joystick stickZero,Joystick stickTwo,AHRS ahrs, Encoder encLeft, Encoder encRight){
		this.ahrs = ahrs;
		this.driveyStick = stickZero;
		this.encLeft = encLeft;
		this.encRight = encRight;
		pins =  new PinsClass();
		driveSpeed = new DoubleSolenoid(pins.driveSolenoidA,pins.driveSolenoidB);
		frontLeftMotor = new CANTalon(pins.frontLeftMotorPin);
		//middleLeftMotor = new CANTalon(pins.middleLeftMotorPin);
		rearLeftMotor = new CANTalon(pins.rearLeftMotorPin);
		frontRightMotor = new CANTalon(pins.frontRightMotorPin);
		//middleRightMotor = new CANTalon(pins.middleRightMotorPin);
		rearRightMotor = new CANTalon(pins.rearRightMotorPin);
		
		roboDrive = new RobotDrive(frontLeftMotor,rearLeftMotor,frontRightMotor,rearRightMotor);
		
		//middleLeftMotor.changeControlMode(TalonControlMode.Follower);
    	//middleRightMotor.changeControlMode(TalonControlMode.Follower);
    	
    	//middleLeftMotor.set(pins.frontLeftMotorPin); 							
    	//middleRightMotor.set(pins.frontRightMotorPin);
	}

	public double calcEncoderDistance(){
//		double ticksPerRot = 100.0;
//		double ratioEncWheel = (66.0/18.0);
//		double wheelDiameter = (7.5)*0.0254; //7.5 is our wheel diameter while 0.0254 is meter per inch
//		double wheelRotations;
		double length;
		double encoderLeft = encLeft.get();
		double encoderRight = encRight.get();
//		wheelRotations = (((encoderLeft / ticksPerRot) + ((encoderRight) / ticksPerRot)) / 2.0)*ratioEncWheel;
//		length = ((wheelDiameter*Math.PI)*wheelRotations)/15.007;
//		return length;
		//1m = 667
		length = ((encoderLeft/575)+ (encoderRight/575))/2;
		return length;
		
	}
	
	public void driveStraightCompass(){
		double turnSpeed;
		double calAngle;
		double startAngle = ahrs.getAngle();
		SmartDashboard.putNumber("Start Angle:", startAngle);
		while (true){
			calAngle = calcAngle(startAngle, ahrs.getAngle());
			SmartDashboard.putNumber("Calculated Angle:", calAngle);
			
			if (calAngle<0){
				turnSpeed = proportional(calAngle);
				SmartDashboard.putNumber("Turn Speed:", turnSpeed);
				SmartDashboard.putNumber("Left Motor:", -0.5);
				SmartDashboard.putNumber("Right Motor:", (-0.5+(turnSpeed*0.2)));
				roboDrive.tankDrive((0.5)*-1, ((0.5) + (turnSpeed * 0.2))*-1);
			}
			else{
				turnSpeed = proportional(calAngle);
				SmartDashboard.putNumber("Turn Speed:", turnSpeed);
				SmartDashboard.putNumber("Right Motor:", -0.5);
				SmartDashboard.putNumber("Left Motor:", (-0.5+(turnSpeed*0.2)));
				roboDrive.tankDrive((0.5) + ((turnSpeed * 0.2))*-1, (0.5)*-1);
			}
		}
		
	}
	
	
	public void drivey(double distance){
		while (calcEncoderDistance() < distance /*&& encLeft.get() < distance*/){
			
			SmartDashboard.putNumber("Encoder Distance Travelled", calcEncoderDistance());
			SmartDashboard.putNumber("Encoder Distance Wanted", distance);
			SmartDashboard.putNumber("Right Encoder Diff", encRight.get()-encLeft.get());
			SmartDashboard.putNumber("Left Encoder Diff", encLeft.get()-encRight.get());
			SmartDashboard.putNumber("Right Encoder", encRight.get());
			SmartDashboard.putNumber("Left Encoder", encLeft.get());


			
			if (encRight.get()-encLeft.get() > 3){
				roboDrive.tankDrive(-0.55, -0.5);
			}
			else if( encLeft.get()-encRight.get() > 3){
				roboDrive.tankDrive(-0.5, -0.55);
			}
			else{
				roboDrive.tankDrive(-0.5, -0.5);
			}
		}
		roboDrive.tankDrive(0, 0);
		}
		
	
	public void driveStraight(double distance, double angle){
		//double calAngle;
		//double turnSpeed;
		double distanceTraveled;
		double straightSpeed;
    	distanceTraveled = calcEncoderDistance();
		while ((distance-distanceTraveled)>0.1){
//	    	calAngle = calcAngle(angle, ahrs.getAngle());
//	    	distanceTraveled = calcEncoderDistance();
//	    	straightSpeed = proportionalDis(distance,(distance-distanceTraveled));
//	
//	    	if (calAngle<0){
//				turnSpeed = proportional(calAngle);
//				roboDrive.tankDrive((straightSpeed)*-1, ((straightSpeed) + (turnSpeed * 0.2))*-1);
//			}
//			else{
//				turnSpeed = proportional(calAngle);
//				roboDrive.tankDrive((straightSpeed) + ((turnSpeed * 0.2))*-1, (straightSpeed)*-1);
//			}
			
			
	//    	roboDrive.tankDrive((straightSpeed)*-1, (straightSpeed)*-1);
	    	
	    	if (distanceTraveled < (distance +0.2) || distanceTraveled > (distance - 0.2)) {
	    		roboDrive.tankDrive(0, 0);
	    	}
	    	
	    	
		}		
	}
	
	private double calculateXAxis(){
		double yAxis;
        if (driveyStick.getX() < -0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick.getX())),expFactor)+stall)*negOne;
        }
        else if (driveyStick.getX() > 0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick.getX())),expFactor)+stall);
        }
        else{
        	yAxis = 0.0;
        }
        
        return yAxis;


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
	//see this git f**ked this up
	
	
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
		roboDrive.tankDrive((calculateYAxis()), (calculateThrottleAxis()));
	}
	
	//Drive the Robot In Arcade Drive
	public void arcadeDrive(){
		roboDrive.arcadeDrive((calculateYAxis()),(calculateTwistAxis()));
	}
	

	
	//Drive Forward For A Certain Amount of Time
	public void driveTime(double speed, double time){
		roboDrive.drive(speed, 0);
		Timer.delay(time);
		roboDrive.drive(0, 0);
	}
		

	//Calculate the Angle Difference
    public double calcAngle(double targetAngle, double currentAngle){
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
    
    private static double proportionalDis(double distanceWant,double distanceDelta){
    	double speed;
    	speed = (1-stall)*(distanceDelta/distanceWant)+stall;
    	return speed;
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
        while(abs(calcAngle(targetAngle, ahrs.getAngle())) > deadBand){
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
        while(abs(calcAngle(forwardAngle, ahrs.getAngle())) > deadBand){
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
		driveSpeed.set(DoubleSolenoid.Value.kReverse);
		
	}
	
	public void highGear(){
		driveSpeed.set(DoubleSolenoid.Value.kForward);

	}
	
	public void passivePosition(){
		driveSpeed.set(DoubleSolenoid.Value.kOff);
	}
	
	public void drive(double speedLeft, double speedRight){
		roboDrive.tankDrive(speedLeft,speedRight);
		
	}
	
	
	private double calculateXAxisJoy(){
		double yAxis;
        if (driveyStick.getX() < -0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick.getX())),expFactor)+stall)*negOne;
        }
        else if (driveyStick.getX() > 0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick.getX())),expFactor)+stall);
        }
        else{
        	yAxis = 0.0;
        }
        
        return yAxis;


	}
	
	
	private double calculateYAxisJoy(){
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
	
	public void arcadeDriveSingle(){
		roboDrive.arcadeDrive(calculateYAxisJoy(),calculateXAxisJoy());
	}
}

