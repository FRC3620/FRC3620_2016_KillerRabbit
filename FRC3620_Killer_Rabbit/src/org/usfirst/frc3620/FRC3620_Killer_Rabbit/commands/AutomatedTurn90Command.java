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
public class AutomatedTurn90Command extends Command implements PIDOutput{
	
	AHRS ahrs;
	double rotateToAngleRate;
	
	static final double kP = .03;
	static final double kI = .00;	
	static final double kD = .00;
	static final double kF = .00;
	
	static final double kToleranceDegrees = 2.0f;
	
	
	
    public AutomatedTurn90Command() {
    	
    	
    	
        requires(Robot.driveSubsystem);
        // Use requires() here to declare subsystem dependencies
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean rotateToAngle = true;
    	double currentRotationRate;
    	PIDController turnController = new PIDController(kP, kI, kD, kF, ahrs, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        
        turnController.setSetpoint(90.0f);
        if(rotateToAngle) {
        	turnController.enable();
        	currentRotationRate = rotateToAngleRate;
        } else {
        	turnController.disable();
            currentRotationRate = 0;
        }

        
        Robot.driveSubsystem.setDriveForward(0.0,currentRotationRate);
    	
    	
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
    
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
