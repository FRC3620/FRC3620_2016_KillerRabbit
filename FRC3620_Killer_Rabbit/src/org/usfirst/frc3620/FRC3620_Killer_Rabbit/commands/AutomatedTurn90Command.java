package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class AutomatedTurn90Command extends Command{
	
AHRS ahrs;
	
	

	
	
	

    public AutomatedTurn90Command() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ahrs.reset();
    
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
  
    
    
   
}
