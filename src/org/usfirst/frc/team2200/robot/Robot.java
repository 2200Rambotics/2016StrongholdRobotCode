
package org.usfirst.frc.team2200.robot;

import com.ni.vision.NIVision;


import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;

import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

/*
 * Made some changes to code in a different branch in case we messed anything up!
 */

public class Robot extends SampleRobot {
	AHRS ahrs;
	DriveClass drive;
	AutoClass auto;
	PinsClass pins;
	BallPickupClass ballPickup;
	TelescopingArmClass teleArm;
    Joystick moveyController;
    Joystick stick;
    Joystick shootyStick;
    
    int session;
    Image frame;
    SendableChooser positionSender;
    SendableChooser defenseSender;
    Solenoid variableSpeed;
    Encoder leftEnc;
    Encoder rightEnc;
    /*
    DigitalInput limitSwitchTeleArm;
    DigitalInput teleArmInfrared;
*/
    

    public Robot() {
    	
    	//Check if the NAVX Sensor Can Be Found
        // Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB   
        try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (Exception e) {			
			e.printStackTrace();
		} 
        pins = new PinsClass();
        //Two Line Sensors on DIO Slots 0 and 2

    	
        
    	//Logitec Controller Used for Driving on Slot 0 Joystick Used for Functional Mech on Slot 1 
    	//**Check Driver Station for USB Joystick Slots**
        moveyController = new Joystick(0);
        shootyStick = new Joystick(1);
        
        leftEnc = new Encoder(pins.leftEncPinA,pins.leftEncPinB,true,EncodingType.k1X);
        rightEnc = new Encoder(pins.rightEncPinA,pins.rightEncPinB,true,EncodingType.k1X);
        // Telearm sensors
        //limitSwitchTeleArm = new DigitalInput(pins.limitSwitchTeleArm);
        //teleArmInfrared = new DigitalInput(pins.teleArmInfrared);
        
        
        //Declaring The Constructers of Other Classes
        drive = new DriveClass(moveyController,ahrs,leftEnc,rightEnc, this, stick);
        ballPickup = new BallPickupClass();
        auto = new AutoClass(drive,ballPickup);
        teleArm = new TelescopingArmClass();

        leftEnc.reset();
        rightEnc.reset();
   
    }
    
    public void robotInit() { 

          // the camera name (ex "cam0") can be found through the roborio web interface
    	/*
    	try{
        	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    		session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);NIVision.IMAQdxConfigureGrab(session);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	*/
    	
    	//Create the Choosers For Selecting Auto 
    	//One for Robot Position the Other for Defense Driving Through
        positionSender = new SendableChooser();
        defenseSender = new SendableChooser();
        
        //Adding the Objects For the Position Chooser
        positionSender.addDefault("4", "4");
        positionSender.addObject("3", "3");
        positionSender.addObject("2", "2");
        positionSender.addObject("1", "1");
        
        //Adding the Objects for the Defense Chooser
        defenseSender.addDefault("Low Bar","Low Bar");
        defenseSender.addObject("Ramparts", "Ramparts");
        defenseSender.addObject("Moat", "Moat");
        defenseSender.addObject("Rough Terrain", "Rough Terrain");
        defenseSender.addObject("Rock Wall", "Rock Wall");


        //Put the Choosers Onto the SmartDashboard
        SmartDashboard.putData("Robots Position", positionSender);
        SmartDashboard.putData("Defense Type", defenseSender);

        
    }


    public void autonomous() {
    	//SAFETY
    	
        drive.roboDrive.setSafetyEnabled(false);
        
        drive.frontRightMotor.enableBrakeMode(true);
        drive.frontLeftMotor.enableBrakeMode(true);
        drive.rearRightMotor.enableBrakeMode(true);
        drive.rearLeftMotor.enableBrakeMode(true);

        
        
    	String defenseSelected = (String) defenseSender.getSelected();
    	String positionSelected = (String) positionSender.getSelected();
    	
        SmartDashboard.putNumber("Left Encoder:", leftEnc.getDistance());
        SmartDashboard.putNumber("Right Encoder:", rightEnc.getDistance());
    	SmartDashboard.putNumber("Calc Encoder distance", drive.calcEncoderDistance());
        
    	//The Autonomous to Run if Low Bar is Selected
    	if (defenseSelected == "Low Bar"){
			SmartDashboard.putNumber("Enc M", drive.calcEncoderM());
    		drive.driveStraightCompass(0.8, 3);
			SmartDashboard.putNumber("Enc M", drive.calcEncoderM());

    		/*
    		leftEnc.reset();
    		rightEnc.reset();
    		drive.driveStraightCompass(0.8);
    		if (drive.calcEncoderDistance() == 575){
    			drive.roboDrive.tankDrive(0, 0);
    		
    		}
    		*/
    		
    		//drive.drivey(3);
    		//auto.lowGoal();

    	}
    	//The Autonomous to Run if Ramparts is Selected
    	else if (defenseSelected == "Ramparts"){
    		
    		//The Different Autos to Run Depending on Positions
    		if (positionSelected == "1"){
    			auto.rampartsOne();
    			
    		}
    		else if (positionSelected == "2"){
    			auto.rampartsTwo();
    			
    		}
    		else if (positionSelected == "3"){
    			auto.rampartsThree();
	
    		}
    		else if (positionSelected == "4"){
    			auto.rampartsFour();
    			
    		}
    		
    	}
    	//The Autonomous to Run if Rough Terrain is Selected
    	else if (defenseSelected == "Rough Terrain"){
    		
    		//The Different Autos to Run Depending on Positions
    		if (positionSelected == "1"){
    			auto.roughTerrainOne();
    			
    		}
    		else if (positionSelected == "2"){
    			auto.roughTerrainTwo();
    			
    		}
    		else if (positionSelected == "3"){
    			auto.roughTerrainThree();
    			
    		}
    		else if (positionSelected == "4"){
    			auto.roughTerrainFour();
    			
    		}
    		
    	}
    	else if (defenseSelected == "Rock Wall"){
    		
    		//The Different Autos to Run Depending on Positions
    		if (positionSelected == "1"){
    			auto.rockWallOne();
    			
    		}
    		else if (positionSelected == "2"){
    			auto.rockWallTwo();
    			
    		}
    		else if (positionSelected == "3"){
    			auto.rockWallThree();
    			
    		}
    		else if (positionSelected == "4"){
    			auto.rockWallFour();
    			
    		}
    		
    	}
    	//The Autonomous to Run if Moat is Selected
    	else if (defenseSelected == "Moat"){
    		
    		//The Different Autos to Run Depending on Positions
    		if (positionSelected == "1"){
    			auto.moatOne();
    			
    		}
    		else if (positionSelected == "2"){
    			auto.moatTwo();
    			
    		}
    		else if (positionSelected == "3"){
    			auto.moatThree();
    			
    		}
    		else if (positionSelected == "4"){
    			auto.moatFour();
    			
    		}
    	
    	}

    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
    	//SAFETY
    	leftEnc.reset();
    	rightEnc.reset();
        drive.roboDrive.setSafetyEnabled(true);
        double tankMode = 0;
        
        drive.frontRightMotor.enableBrakeMode(false);
        drive.frontLeftMotor.enableBrakeMode(false);
        drive.rearRightMotor.enableBrakeMode(false);
        drive.rearLeftMotor.enableBrakeMode(false);
        boolean highFlip = true;
        
        while (isOperatorControl() && isEnabled()) {
//        	try{
//        		NIVision.IMAQdxGrab(session, frame, 1);
//        	}
//        	catch(Exception e){
//        		e.printStackTrace();
//        	}
             //NIVision.imaqDrawShapeOnImage(frame, frame, rect,
             //        DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
            /* 
            try {
				CameraServer.getInstance().setImage(frame);
			} catch (Exception e) {
				e.printStackTrace();
			}
*/
            //auto.testUltra();
            
            SmartDashboard.putBoolean("Left",AutoClass.leftSensor.get());
            SmartDashboard.putBoolean("Right",AutoClass.rightSensor.get());
            SmartDashboard.putNumber("ultra range", auto.getLeftUltra());
            
            SmartDashboard.putNumber("Front Left Motor", drive.frontLeftMotor.getOutputCurrent());
//           // SmartDashboard.putNumber("Middle Left Motor", drive.middleLeftMotor.getOutputCurrent());
            SmartDashboard.putNumber("Rear Left Motor", drive.rearLeftMotor.getOutputCurrent());
//            
            SmartDashboard.putNumber("Front Right Motor", drive.frontRightMotor.getOutputCurrent());
//          //  SmartDashboard.putNumber("Middle Right Motor", drive.middleRightMotor.getOutputCurrent());
            SmartDashboard.putNumber("Rear Right Motor", drive.rearRightMotor.getOutputCurrent());
            
            SmartDashboard.putNumber("Left Encoder:", leftEnc.get());
            SmartDashboard.putNumber("Right Encoder:", rightEnc.get());
            
            SmartDashboard.putNumber("Both Encoders:", (leftEnc.get()-rightEnc.get()));
            
            SmartDashboard.putNumber("Left Encoder Dis:", leftEnc.getDistance());
            SmartDashboard.putNumber("Right Encoder Dis:", rightEnc.getDistance());
            
            
            
            if(ballPickup.armUp){
            	SmartDashboard.putString("Intake Arms:","Down" );
            }
            else{
            	SmartDashboard.putString("Intake Arms:","Up");
            }
            
            if(teleArm.armUp){
            	SmartDashboard.putString("Lift Arm:","Up" );
            }
            else{
            	SmartDashboard.putString("Lift Arm:","Down" );
            }
            
            SmartDashboard.putNumber("Angle:", ahrs.getAngle());
            SmartDashboard.putNumber("X:", ahrs.getDisplacementX());
            double length = drive.calcEncoderDistance();
            SmartDashboard.putNumber("Meters Travelled", length);



            
            
           /* //When the Trigger is Pressed Shoot
            if(shootyStick.getRawButton(1)){
            	ballPickup.shoot(1.0);
            }
            */
            /*  //Joystick Controlls
            //When the Joystick Button 2 is Pressed Roll The Intake Rollers to Intake
            if(shootyStick.getRawButton(2)){
            	ballPickup.intake(1.0);
            }
            
            //When Joystick Button 3 is Pressed Roll the Intake Rollers to Open the Portcullis
            else if(shootyStick.getRawButton(3)){
            	ballPickup.outake(1.0);
            }
            else{
            	ballPickup.stop();
            }
            //When Pulling Back on The Joystick Raise the Intake Arms 
            if(shootyStick.getY() < -0.9){
            	ballPickup.upPosition();
            }
            
            //When Pushing Forward on The Joystick Lower the Intake Arms 
            else if(shootyStick.getY() > 0.9){
            	ballPickup.downPosition();
            }
            
            //When not Pushing or Pulling on the Joystick Stop Moving the Arms
            else if(shootyStick.getY() > -0.9 || shootyStick.getY() < 0.9){
            	ballPickup.passivePosition();
            }

            
            //While Button 6 is Being Pressed Unlock and Move Up the Arm
            if(shootyStick.getRawButton(6)){
            	//unlock and up
            	
            	teleArm.locked();
            }
            
            //While Button 7 is Being Pressed Lock the Arm
            else if(shootyStick.getRawButton(7)&& shootyStick.getRawButton(8)){
            	//lock
            	teleArm.unlockedAndUp();
            }
            
            //If Neither Button is Selected Stop the Arm
            else{
            	teleArm.passivePosition();
            }
            
            
            //Extend the Telescoping Arm While Pressing Button 11
            if(shootyStick.getRawButton(11) && teleArmInfrared.get()){
            	  teleArm.extend(1);
            }
            if (!teleArmInfrared.get()){
            	teleArm.stop();
            }
            }
            if (limitSwitchTeleArm.get()==false){ //SWITCH needs to be Normally Closed (NC) wired. 
            	teleArm.stop();
            	teleArm.locked();
    }

            //Retract the Telescoping Arm While Pressing Button 10
            else if(shootyStick.getRawButton(10) && limitSwitchTeleArm.get()){ //DOWN
            	teleArm.retract(1);
            }
            
            else if (shootyStick.getRawButton(9)){//UP
            	teleArm.retract(0.2);
            }
            
            //If Neither Button is Being Pressed Stop the Arm
            else{
            	teleArm.stop();
            }
            */
            

            
            if(shootyStick.getRawButton(2)){
            	ballPickup.openDoor(1);
            		
            }

            
            //When Pulling Back on The Joystick Raise the Intake Arms 
            if(shootyStick.getRawButton(5)){
            	ballPickup.upPosition();
            }
            //When Pushing Forward on The Joystick Lower the Intake Arms 
            else if(shootyStick.getRawButton(7)){
            	ballPickup.downPosition();
            }
            
            //When not Pushing or Pulling on the Joystick Stop Moving the Arms
            /*
            else{
            	ballPickup.passivePosition();
            }
            */
            
            
            ballPickup.outake(shootyStick.getThrottle());
            
            teleArm.extend(shootyStick.getY());
            
            //When the Joystick Button 2 is Pressed Roll The Intake Rollers to Intake



            
            //While Button 6 is Being Pressed Unlock and Move Up the Arm
            if(shootyStick.getRawButton(4)){
            	//unlock and up
            	
            	teleArm.locked();
            }
            
            //While Button 7 is Being Pressed Lock the Arm
            else if(shootyStick.getRawButton(9)&& shootyStick.getRawButton(10)){
            	//Final Form Scorpion Tail!
            	teleArm.unlockedAndUp();
            }
            
            //If Neither Button is Selected Stop the Arm
            else{
            	teleArm.passivePosition();
            }
            
         /*   

            if (!teleArmInfrared.get()){
            	teleArm.stop();
            }
            }
            if (limitSwitchTeleArm.get()==false){ //SWITCH needs to be Normally Closed (NC) wired. 
            	teleArm.stop();
            	teleArm.locked();
    }



            */
            //Change the Gear to Low Gear by Clicking Button 7
            if(moveyController.getRawButton(7)){
            	SmartDashboard.putString("Button:","7");
            	drive.lowGear();
				highFlip = false;
            }
            
            //Change the Gear to High Gear by Clicking Button 8
            else if(moveyController.getRawButton(8)){
            	drive.lowGear();
            	SmartDashboard.putString("Button:","8");
				highFlip = false;
            }
            //
            //If Neither Button is Being Pressed Set A and B to Zero
            else{
            	if (highFlip) {
					drive.passivePosition();
				} else {
					drive.highGear();
					highFlip = true;
				}
            }

            drive.tankDrive();
         /*   if(moveyController.getRawButton(1)){
            	SmartDashboard.putString("Button:","1");
            	if(tankMode == 0){
            		tankMode = 1;
            		Timer.delay(0.5);
            	}
            	else if(tankMode == 1){
            		tankMode = 0;
            		Timer.delay(0.5);
            	}


            }

        	if (tankMode == 0){
        		SmartDashboard.putString("Drive Mode:", "Corey Drive");
        		drive.tankDrive();
        	}
        	else if(tankMode == 1){
        		SmartDashboard.putString("Drive Mode:", "Jana Drive");
        		drive.arcadeDrive();
        	}
        	*/
        	SmartDashboard.putNumber("Wheel Rotations:", drive.wheelRotations(leftEnc.get(),rightEnc.get()));

            
            
            Timer.delay(0.005);	
        }}
    // wait for a motor update time
        
/*
        try {
			NIVision.IMAQdxStopAcquisition(session);
		} catch (Exception e) {
			e.printStackTrace();
		}}
    }
    */
    
    public static boolean getLeftLineSensor() {
    	
    	return AutoClass.leftSensor.get();
    	
    }
    
    public static boolean geRightLineSensor() {
    	
    	return AutoClass.rightSensor.get();
    	
    }

    /**
     * Runs during test mode
     */
    public void test() {
    	
    	auto.getLeftUltra();

    }
}