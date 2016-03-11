// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

/**
 *
 */
public class ShootBallCommand extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public ShootBallCommand() {

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
	}

	// Called just before this Command runs the first time
	Timer timer = new Timer();
	boolean canIShoot;

	protected void initialize() {
		// we will only do this if the
		if (Robot.armSubsystem.getEncoderIsValid()) {
			// we have a good encoder, check the arm
			canIShoot = Robot.armSubsystem.isArmUp();
			if (!canIShoot) {
				logger.info("not shooting, arm is not up");
			}
		} 
		else {
			// we don't have a good encoder, so just allow it....
			canIShoot = true;
		}
		if (!Robot.shooterSubsystem.isShooterSpunUp()) {
			canIShoot = false;
			logger.info("not shooting, shooter is not up to speed");
			
		}
		else{
			logger.info("check shooter was true");
		}
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (canIShoot) {
			if (timer.get() < 0.2) {
				Robot.armSubsystem.nudgeToTop();
			}
			else {
				Robot.armSubsystem.stopNudging();
				Robot.intakeSubsystem.dropBallInShooter();
			}
		} else {
			logger.warn("Bring Arm Up");
		}
	}
  
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if  (!canIShoot) {
			return true;
		}

		return timer.hasPeriodPassed(1);

	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.armSubsystem.stopNudging();
		Robot.intakeSubsystem.intakeStop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
