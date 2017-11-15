package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToTargetCommand extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

    public TurnToTargetCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("TurnToTargetCount started");
    	SmartDashboard.putString("Blob Count", "" + Robot.driveSubsystem.getContourX());
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.turnToTarget();
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       
    	//return Robot.driveSubsystem.getRangeInInches()<10;
    	//Was ended by range sensor, now should be ended by a button press (whenPressed?)
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("TurnToTargetCount ended");
    	Robot.driveSubsystem.stopTurning();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("TurnToTargetCount interrupted");
    	end();
    }
}
