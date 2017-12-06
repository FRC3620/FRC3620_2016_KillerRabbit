// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMode;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.usfirst.frc3620.vision.UDPReceiver;

//import com.ni.vision.NIVision;
//import com.ni.vision.NIVision.DrawMode;
//import com.ni.vision.NIVision.Image;
//import com.ni.vision.NIVision.ShapeMode;
//import com.ni.vision.VisionException;

import java.util.Timer;
import java.util.TimerTask;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	double automaticHeading = 0;

	AHRS ahrs;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController leftFront = RobotMap.driveSubsystemLeftFront;
    private final SpeedController leftRear = RobotMap.driveSubsystemLeftRear;
    private final SpeedController rightFront = RobotMap.driveSubsystemRightFront;
    private final SpeedController rightRear = RobotMap.driveSubsystemRightRear;
    private final RobotDrive robotDrive41 = RobotMap.driveSubsystemRobotDrive41;
    private final Encoder leftDriveEncoder = RobotMap.driveSubsystemLeftDriveEncoder;
    private final Encoder rightDriveEncoder = RobotMap.driveSubsystemRightDriveEncoder;
    
    private final SpeedController turretTalon = RobotMap.driveSubsystemTurretTalon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	//shooterVoltage = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("Shooter Voltage", 9.0);
    
    boolean haveFrontCamera = false;
	boolean haveRearCamera = false;
	
	boolean kidz = edu.wpi.first.wpilibj.Preferences.getInstance().getBoolean("Kidz", false);
	double kidzKonstant = .75;

	public void turnTurret(double speed) {

		if (speed > 0) {
			turretTalon.set(speed);
			/*
			// we are trying to go to the right
			if (isRightLimitSwitchDown()) {
				// we are up against the right limit switch, so turn off motor
				leftRightTalon.set(0.0);
			} else {
				// we are not up against the right limit swtich, so run the motor
				// at whatever speed we were asked for
				leftRightTalon.set(speed);
			}
			*
			*/
			
		} else {
			turretTalon.set(speed);
			/*
			// we are trying to go to the left
			if (isLeftLimitSwitchDown()) {
				// we are up against the left limit switch, so turn off motor
				leftRightTalon.set(0.0);
			} else {
				// we are not up against the left limit swtich, so run the motor
				// at whatever speed we were asked for
				leftRightTalon.set(speed);
			}
			*
			*/
		}

	}
	
	public void arcadeDrive() {
		robotDrive41.arcadeDrive(Robot.oi.driverJoystick);

	}
	
	public void setDriveForward(double move, double rotate) {
		if (Math.abs(move) <= 0.2) {
			move = 0;
		}
		if (Math.abs(rotate) <= 0.2) {
			rotate = 0;
		}
		robotDrive41.arcadeDrive(move , rotate);

	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

//*****************************************VISION************************************************
    
    boolean complainedAboutNoVision = false;
    
	public double getTargetLocation(){
		if (UDPReceiver.visionData == null) {
			if (!complainedAboutNoVision) {
				logger.warn("X not recieved");
			}
			complainedAboutNoVision = true;
			return 0;
		}
		complainedAboutNoVision = false;
		return UDPReceiver.visionData.getX();
	}
    
	public double getTargetCenter(){
		if (UDPReceiver.visionData == null){
			if (!complainedAboutNoVision) {
				logger.warn("ImageWidth not recieved");
			}
			complainedAboutNoVision = true;
			return 0;
		}
		complainedAboutNoVision = false;
		return UDPReceiver.visionData.getImageWidth()/2;
	}
	
	public boolean isContourThere(){
		return getContourX() != null;
	}
	
	public Double getContourX(){
		if (UDPReceiver.visionData == null){
			if (!complainedAboutNoVision) {
				logger.warn("ImageWidth not recieved");
			}
			complainedAboutNoVision = true;
			return null;
		}
		complainedAboutNoVision = false;
		return UDPReceiver.visionData.getX();
	}
	
	public double xOffset(){
		return getTargetLocation();
	}
	
	/*
	double alignment;
	
	public double getAlignment(){
		if(isLeftSideBlocked()){
			alignment=getTargetCenter();
			return alignment;
		}
		else if(!isLeftSideBlocked()){
			alignment=getTargetCenter();
			return alignment;
		}
		else{
			return getTargetCenter();
		}
	}
	*
	*/

	public boolean robotIsAligned(){
		if (Math.abs(xOffset())<10){
			return true;
		}
		else{
			return false;
		}
	}
	
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^VISION^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^	
	
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new DriveCommand());
	}

	public void stopMotors() {
		robotDrive41.stopMotor();
		// TODO Auto-generated method stub

	}
	
	static public double angleDifference (double angle1, double angle2 ) {
		double diff = Math.abs(angle1 - angle2);
		if (diff > 180) {
			diff = 360 - diff;
		}
		return diff;
	}

	/**
	 * Bring an angle into the range of 0..360.
	 * -10 turns into 350
	 * 400 turns into 40
	 * @param angle
	 * @return angle brought into range 0..360
	 */
	static public double normalizeAngle(double angle) { 
		// bring into range of -360..360
		double newAngle = angle % 360;
		
		// if it's between -360..0, put it between 0..360
		if (newAngle < 0)
			newAngle += 360;
		
		return newAngle;
	}

	public double getAutomaticHeading() {
		return automaticHeading;
	}

	public double changeAutomaticHeading(double changeAngle) {
		automaticHeading = automaticHeading + changeAngle;
		automaticHeading = normalizeAngle(automaticHeading);
		return automaticHeading;

	}
	
	public void allInit(RobotMode robotMode)
	{
		if(robotMode==RobotMode.TELEOP || robotMode==RobotMode.AUTONOMOUS)
		{
			resetNavX();
			logger.info("NavX is resetting");
			resetEncoders();
			automaticHeading = 0;
		}

	}

	public void resetEncoders()
	{
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
		
	}

	public void resetNavX() 
	{
		ahrs.resetDisplacement();
		logger.info("Resetting X Displacement, X = {}", ahrs.getDisplacementX());
		ahrs.reset();
		logger.info("Resetting NavX Angle, Angle = {}", ahrs.getAngle());
	}

	public DriveSubsystem() {
		super();
		
		ahrs = new AHRS(SPI.Port.kMXP);
        logger.info("NaxX connected = {}, firmware = {}", ahrs.isConnected(), ahrs.getFirmwareVersion());
	}


	boolean weHaveAlreadyWarned = false;

	public void turnToTarget(){
//		tellVisionWhichSide();
		if(isContourThere()){
	    	System.out.println("Turning to target");
			
			if(robotIsAligned()){
				System.out.println("Robot is Aligned");
				SmartDashboard.putBoolean("Robot Is Aligned", true);
				//slideMotor(0);
				//Make robot stop turning here:
				stopMotors();
			}
			else if(xOffset()<0){
				SmartDashboard.putBoolean("Robot Is Aligned", false);
				//Turn robot/turret one way here:
				//turnTurret(.2);
			}
			else if(xOffset()>0){
				SmartDashboard.putBoolean("Robot Is Aligned", false);
				//Turn robot/turret the other way here:
				//turnTurret(-.2);
			}
			
		}
		else {
			System.out.println("Blob not Found");
			SmartDashboard.putBoolean("Robot Is Aligned", false);
			//For now, stop drive motors if we don't see a blob.
			//slideMotor(0);
		}
	}
	
	public void stopTurning() {
		//Set drive motors to zero:
		stopMotors();
	}
	
	public void updateDashboard() {
    	SmartDashboard.putNumber("TargetCenter", getTargetLocation());
    	SmartDashboard.putNumber("BlobCount", getContourX());
    	
    }
	
    public double getRoll() {
        return ahrs.getRoll();
    }
    public double getAngle() {
        return ahrs.getAngle();
    }
    public double getPitch() {
        return ahrs.getPitch();
    }
    public double getAccelX() {
        return ahrs.getRawAccelX();
    }
    public double getAccelY() {
        return ahrs.getRawAccelY();
    }
    public double getAccelZ() {
        return ahrs.getRawAccelZ();
    }
    public double getDisplacementX() {
        return ahrs.getDisplacementX();
    }
    public double getDisplacementY() {
        return ahrs.getDisplacementY();
    }
    public double getDisplacementZ() {
        return ahrs.getDisplacementZ();
    }
    public AHRS getAhrs() {
        return ahrs;
    }
}
