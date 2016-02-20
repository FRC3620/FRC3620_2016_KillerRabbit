package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterTiltCommand extends Command {

    public ShooterTiltCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftTrigger = Robot.oi.operatorJoystick.getRawAxis(2);
		double rightTrigger = Robot.oi.operatorJoystick.getRawAxis(3);

		// double trigger = leftTrigger - rightTrigger;
		if (leftTrigger > 0.2 || rightTrigger > 0.2) {
			// the driver is leaning on the triggers. let's go into manual mode
			if (leftTrigger > 0.2) {
				Robot.shooterSubsystem.moveShooterPositionUp();
			} else if (rightTrigger > 0.2) {
				Robot.shooterSubsystem.moveShooterPositionDown();
			}
		}
		else
		{
			Robot.shooterSubsystem.stopShooterPositionTalon();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.stopShooterPositionTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
