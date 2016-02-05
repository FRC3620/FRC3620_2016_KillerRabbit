package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 */
public class AutomatedMove extends Command implements PIDOutput{
	
	AHRS ahrs;
	
	
	static final double kP = .03;
	static final double kI = .00;	
	static final double kD = .00;
	static final double kF = .00;
	double sideStick;
	
	PIDController pidDriveStraight = new PIDController(kP, kI, kD, ahrs, this);
	

    public AutomatedMove() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	pidDriveStraight.setOutputRange(-.50, .50);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ahrs.reset();
    	pidDriveStraight.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("sideStick value: " + sideStick);
    	Robot.driveSubsystem.setDriveForward(.50, sideStick);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	pidDriveStraight.disable();
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
  
    
    public void pidWrite(double output) {
       sideStick = output;
    }
   
}
