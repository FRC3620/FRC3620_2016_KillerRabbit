package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterSetPositionCommand extends Command {

	double whatPositionWeWant = 0;
	
    public ShooterSetPositionCommand(double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	whatPositionWeWant = position;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
