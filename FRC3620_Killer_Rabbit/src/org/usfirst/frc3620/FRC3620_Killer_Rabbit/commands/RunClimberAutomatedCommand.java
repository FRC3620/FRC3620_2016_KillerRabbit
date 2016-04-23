package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunClimberAutomatedCommand extends Command {
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	boolean climberHasBeenRun = false;
	
    public RunClimberAutomatedCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	climberHasBeenRun = Robot.liftSubsystem.climberExtendHasBeenRun();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!climberHasBeenRun){
    	logger.info("Climber is going up");
    	Robot.liftSubsystem.climberUp();
    	}
    	else{
    		logger.info("Climber has been extended already, cannot run");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return climberHasBeenRun;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.liftSubsystem.climberStop();
    	logger.info("RunClimber end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("RunClimber interrupted");
    	end();
    }
}
