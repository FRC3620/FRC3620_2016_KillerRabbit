package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class AutomatedMoveWithoutPID extends Command {
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	
	double howFarWeWantToMove = 0;
	double howFastToMove = 0;
	

    public AutomatedMoveWithoutPID(double howFar, double howFast) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
   
    	howFastToMove = howFast;
    	howFarWeWantToMove = howFar;
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {

    	logger.info("AutomatedMove start");
    	RobotMap.driveSubsystemLeftDriveEncoder.reset();
    	RobotMap.driveSubsystemRightDriveEncoder.reset();
//        pidDriveStraight.setSetpoint(Robot.driveSubsystem.getAutomaticHeading());
//    	pidDriveStraight.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Robot.driveSubsystem.setDriveForward(-howFastToMove, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	//logger.info("Left Encoder: {}", RobotMap.driveSubsystemLeftDriveEncoder.getDistance());
    	//logger.info("Right Encoder: {}", RobotMap.driveSubsystemRightDriveEncoder.getDistance());
    	
    	if(RobotMap.driveSubsystemLeftDriveEncoder.getDistance() > howFarWeWantToMove)
    	{
    		return true;
  
    	}
    	else if(RobotMap.driveSubsystemRightDriveEncoder.getDistance() > howFarWeWantToMove)
    	{
    		return true;
    	}
    	
    	else
    	{
    		return false;
    	}

    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("AutomatedMove end");
//    	pidDriveStraight.disable();
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
   
}
