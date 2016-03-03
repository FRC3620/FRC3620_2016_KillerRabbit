package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallIsStuckCommand extends Command {

    public BallIsStuckCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
    //boolean joystickButtonWasPressed = true;
    boolean rollersWereSpinningWhenStarted = false;
    protected void initialize() {
    	logger.info("ballIsStuckStart");
    	//joystickButtonWasPressed = true;
    	rollersWereSpinningWhenStarted = Robot.intakeSubsystem.getweAreUnsticking();
    	}
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (rollersWereSpinningWhenStarted) {
    		//this is if the rollers have already started
    
    	}
    	else { 
    	Robot.intakeSubsystem.ballIsStuck();
    	}
    
    }

    // Make this return true when this Command no longer needs to run execute()
    	protected boolean isFinished() {
        	if (rollersWereSpinningWhenStarted) {
        		
        	}
        	else {
        			//boolean joystickButtonIsPressed = Robot.oi.intakeButton.get();
        			//logger.info("IntakeCommand: ispressed = {}, waspressed = {}", joystickButtonIsPressed, joystickButtonWasPressed);
        			//if (joystickButtonIsPressed && !joystickButtonWasPressed) return true;
        			//joystickButtonWasPressed = Robot.oi.intakeButton.get();
        		if (Robot.intakeSubsystem.ballIsInIntake()) return true;
        	}
            return false;
        }
    

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSubsystem.intakeStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end ();
    }
}
