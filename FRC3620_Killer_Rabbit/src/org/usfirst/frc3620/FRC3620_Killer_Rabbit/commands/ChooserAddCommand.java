package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import java.util.*;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChooserAddCommand extends Command {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	Random random = new Random();

    public ChooserAddCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	List<String> choices = Robot.autoChooser.getChoiceNames();
    	String newSelect = choices.get(random.nextInt(choices.size()));
    	logger.info ("new select = {}", newSelect);
    	Robot.autoChooser.select(newSelect);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
