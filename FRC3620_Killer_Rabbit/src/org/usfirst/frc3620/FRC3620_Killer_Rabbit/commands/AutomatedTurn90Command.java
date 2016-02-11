package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class AutomatedTurn90Command extends Command implements PIDOutput{
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	
	
	AHRS ahrs = Robot.ahrs;
	
	
	static final double kP = .03;
	static final double kI = .00;	
	static final double kD = .00;
	static final double kF = .00;
	double sideStick;
	
	PIDController pidTurn90 = new PIDController(kP, kI, kD, kF, ahrs, this);
	

    public AutomatedTurn90Command() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	pidTurn90.setInputRange(-180.0f,  180.0f);
    	pidTurn90.setOutputRange(-.50, .50);
    	
    	pidTurn90.setContinuous(true);
    	
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	// TODO We need to look at this
    	pidTurn90.setSetpoint(90.0f + ahrs.getAngle());
    	logger.info("AutomatedTurn90 start");
    	pidTurn90.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("PID Error: " + pidTurn90.getError());
    	System.out.println("sideStick value: " + sideStick);
    	Robot.driveSubsystem.setDriveForward(0, sideStick);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("AutomatedTurn90 end");
    	pidTurn90.disable();
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    public void pidGet(double source) {
    	ahrs.getRawGyroX();
    }
      
    
    
    public void pidWrite(double output) {
       sideStick = output;
    }

   
}
