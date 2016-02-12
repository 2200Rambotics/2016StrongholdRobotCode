package org.usfirst.frc.team2200.robot;

public class PinsClass {
	public int frontLeftMotorPin;
	public int middleLeftMotorPin;
	public int rearLeftMotorPin;
	public int frontRightMotorPin;
	public int middleRightMotorPin;
	public int rearRightMotorPin;
	public int topRollerPin;
	public int botRollerPin;
	public int intakeArmSolenoidA;
	public int intakeArmSolenoidB;
	public int teleArmSolenoidA;
	public int teleArmSolenoidB;
	public int teleArmMotor;
	public int driveSolenoidA;
	public int driveSolenoidB;
	public int leftLineSensor;
	public int rightLineSensor;
	public int leftEncPinA;
	public int leftEncPinB;
	public int rightEncPinA;
	public int rightEncPinB;
	
	
	public PinsClass(){
		//PWM Pins
		 topRollerPin = 1;
		 botRollerPin = 2;
		 teleArmMotor = 0;
		 
		 //DIO
		 leftLineSensor = 4;
		 rightLineSensor = 5;
		 
		 leftEncPinA = 0;
		 leftEncPinB = 1;
		 
		 rightEncPinA = 2;
		 rightEncPinB = 3;
		 
		 
		 //CANTalon Addresses
		 frontLeftMotorPin = 11;
		// middleLeftMotorPin = 12;
		 rearLeftMotorPin = 12;
		 
		 frontRightMotorPin = 14;
		// middleRightMotorPin = 15;
		 rearRightMotorPin = 15;
		 
		 //Solenoid Labels
		 //one
		 intakeArmSolenoidA = 6;
		 intakeArmSolenoidB = 1;
		 //three
		 teleArmSolenoidA = 4;
		 teleArmSolenoidB = 3;
		 //two
		 driveSolenoidA = 5;
		 driveSolenoidB = 2;
		 

		
	}

}
