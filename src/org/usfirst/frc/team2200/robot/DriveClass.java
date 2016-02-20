package org.usfirst.frc.team2200.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
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
	Joystick driveyStick2;
	CANTalon frontLeftMotor;
	//CANTalon middleLeftMotor;
	CANTalon rearLeftMotor;
	CANTalon frontRightMotor;
	//CANTalon middleRightMotor;
	CANTalon rearRightMotor;
	AHRS ahrs; 
	PinsClass pins;
	DoubleSolenoid driveSpeed;
	Encoder encLeft;
	Encoder encRight;
	public RobotDrive roboDrive;
	static double negOne = -1.0;
	static double expFactor = 3.0;
	static double deadBand = 1.0;
	static double stall = 0.4;
	static double rotsPerM = (0.858)*10;
	static double ticksPerRot = (1100)*100;
	Robot robotRef;

	public DriveClass(Joystick stickZero,AHRS ahrs, Encoder encLeft, Encoder encRight, Robot robotRef, Joystick stickOne){
		this.ahrs = ahrs;
		this.driveyStick = stickZero;
		this.driveyStick2 = stickOne;
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
		this.robotRef = robotRef;
		
		//middleLeftMotor.changeControlMode(TalonControlMode.Follower);
    	//middleRightMotor.changeControlMode(TalonControlMode.Follower);
    	
    	//middleLeftMotor.set(pins.frontLeftMotorPin); 							
    	//middleRightMotor.set(pins.frontRightMotorPin);
	}


	
	public double wheelRotations(double leftEnc,double rightEnc){
		double diff = (leftEnc+rightEnc)/2;
		SmartDashboard.putNumber("diff",diff);
		double rots =  100.0-((diff- 0.0) * ((0.0- 100.0) / (ticksPerRot - 0.0)) + 100.0);
		SmartDashboard.putNumber("rots",rots);

		return rots;
	}
	public double calcEncoderM(){
		double enRight = -1*(encRight.get());
		double enLeft = encLeft.get();
		double rots = wheelRotations(enLeft,enRight);
		double dis =  10.0-((rots- 0.0) * ((0.0- 10.0) / (rotsPerM - 0.0)) + 10.0);
		return dis;
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
		//1m = 575
//		length = ((encoderLeft/575)+ (encoderRight/575))/2;
		length = (encoderLeft + encoderRight) /2;
		return length;
		
	}
	
    //Calculate the Proportional Speed
    private static double proportional(double deltaAngle){
    	double speed;
    	speed = (1-0.6)*(abs(deltaAngle)/180)+0.6;
    	return speed;
    }
	
	public void driveStraightCompass(double speed,double distance){
		double turnSpeed;
		double calAngle;
		double angleDeadband = 0.0;
		double startAngle = ahrs.getAngle();
		double distanceM = calcEncoderM();
		SmartDashboard.putNumber("Start Angle:", startAngle);
		SmartDashboard.putString("Loop Running?", "Nah");
		SmartDashboard.putNumber("Enc M", distanceM);
		while (distance > distanceM){
            SmartDashboard.putNumber("Angle:", ahrs.getAngle());
			distanceM = calcEncoderM();
			SmartDashboard.putNumber("Enc M", distanceM);
			calAngle = calcAngle(startAngle, ahrs.getAngle());
			SmartDashboard.putNumber("Meters Travelled:", distanceM);
			SmartDashboard.putNumber("Rotations:", this.wheelRotations(encLeft.get(), (-1*(encRight.get()))));
			SmartDashboard.putString("Loop Running?", "Yee");
			if (calAngle<0){
				turnSpeed = proportional(calAngle);
				if (abs(calAngle) < angleDeadband){
					turnSpeed = 0;
				}
				SmartDashboard.putNumber("Turn Speed:", turnSpeed);
				SmartDashboard.putNumber("Left Motor:", -0.5);
				SmartDashboard.putNumber("Right Motor:", ((0.5+(turnSpeed*0.3)))*-1);
				roboDrive.tankDrive((speed)*-1, ((speed) + (turnSpeed * 0.3))*-1);
				
			}
			else{
				turnSpeed = proportional(calAngle);
				if (abs(calAngle) < angleDeadband){
					turnSpeed = 0;
				}
				SmartDashboard.putNumber("Turn Speed:", turnSpeed);
				SmartDashboard.putNumber("Right Motor:", -0.5);
				SmartDashboard.putNumber("Left Motor:", ((0.5+(turnSpeed*0.3)))*-1);
				roboDrive.tankDrive(((speed) + (turnSpeed * 0.3))*-1, (speed)*-1);
			}
		}
		SmartDashboard.putString("Loop Running?", "Nah");
		roboDrive.tankDrive(0, 0);
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
		roboDrive.tankDrive((calculateYAxis()), (calculateThrottleAxis() * -1));
	}
	
	//Drive the Robot In Arcade Drive 
	public void arcadeDrive(){
//		roboDrive.arcadeDrive((calculateYAxisJoy()),(calculateXAxisJoy()));
		roboDrive.arcadeDrive(driveyStick);
	}
	

	
	//Drive Forward For A Certain Amount of Time
	public void driveTime(double speed, double time){
		roboDrive.drive(-1*speed, 0);
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
    

    

	
    //Drive To A Certain Angle
	public void driveAngle(double targetAngle){
        double angle;
        double speed;
        while(abs(calcAngle(targetAngle, ahrs.getAngle())) > deadBand){
            SmartDashboard.putNumber("Angle:", ahrs.getAngle());
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
            SmartDashboard.putNumber("Angle:", ahrs.getAngle());

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
	
	
	private double calculateXAxisJoy(){
		double yAxis;
        if (driveyStick2.getX() < -0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick2.getX())),3)+stall)*negOne;
        }
        else if (driveyStick2.getX() > 0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick2.getX())),3)+stall);
        }
        else{
        	yAxis = 0.0;
        }
        
        return yAxis;


	}
	
	private double calculateYAxisJoy(){
		double yAxis;
        if (driveyStick2.getY() < -0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick2.getY())),3)+stall)*negOne;
        }
        else if (driveyStick2.getY() > 0.1){
        	yAxis = (0.6*Math.pow((Math.abs(driveyStick2.getY())),3)+stall);
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

