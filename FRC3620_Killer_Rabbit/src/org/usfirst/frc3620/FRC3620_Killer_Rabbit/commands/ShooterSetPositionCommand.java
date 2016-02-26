package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterSetPositionCommand extends Command implements PIDSource, PIDOutput{

	double whatValueWeWant = 0;
	
	PIDController shooterPositionPID = new PIDController(0.3, 0, 0, this, this);
	
    public ShooterSetPositionCommand(double desiredVeds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	whatValueWeWant = desiredVeds;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if( whatValueWeWant > Robot.shooterSubsystem.getTiltVeds())
    		Robot.shooterSubsystem.moveShooterPositionUp();
    	else
    		Robot.shooterSubsystem.moveShooterPositionDown();
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

	@Override
	public void pidWrite(double output) {
		Robot.shooterSubsystem.setMoveTilt(output);
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
			
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return Robot.shooterSubsystem.getTiltVeds();
	}
}
