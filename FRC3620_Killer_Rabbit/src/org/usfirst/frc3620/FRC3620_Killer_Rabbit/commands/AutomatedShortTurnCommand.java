package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
//import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.ArmSubsystem;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.DriveSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class AutomatedShortTurnCommand extends Command implements PIDOutput{
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	double sideStick;
	
	double howFarWeWantToTurn = 0;
	
	
	PIDController pidShortTurn = new PIDController(.030, .0005, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	//PIDController pidShortTurn = new PIDController(.030, .0001, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	//PIDController pidShortTurn = new PIDController(.045, .0001, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	//PIDController pidShortTurn = new PIDController(.035, .0001, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	
	public AutomatedShortTurnCommand() {
		this(45);
	}

    public AutomatedShortTurnCommand(double howFar) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	
    	pidShortTurn.setInputRange(0.0f,  360.0f);
    	pidShortTurn.setOutputRange(-1, 1);
    	pidShortTurn.setContinuous(true);
    	
    	howFarWeWantToTurn = howFar;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	logger.info("AutomatedTurn start");
    	double angle = Robot.driveSubsystem.getAutomaticHeading();
    	double newAngle = Robot.driveSubsystem.changeAutomaticHeading(howFarWeWantToTurn);
    	logger.info("angle was {}, new setpoint is {}", angle, newAngle);
    	// TODO We need to look at this
    	pidShortTurn.setSetpoint(newAngle);
    	logger.info("we rechecked the setpoint = {}", pidShortTurn.getSetpoint());
    	pidShortTurn.reset();
    	pidShortTurn.setAbsoluteTolerance(10.0);
    	pidShortTurn.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	SmartDashboard.putNumber("Turn P", pidShortTurn.getP());
    	SmartDashboard.putNumber("Turn I", pidShortTurn.getI());
    	SmartDashboard.putNumber("Turn D", pidShortTurn.getD());
    	
    	SmartDashboard.putNumber("PID Turn Sidestick", sideStick);
    	SmartDashboard.putNumber("PID Angle Setpoint", pidShortTurn.getSetpoint());
    	SmartDashboard.putNumber("PID Short Angle Error", pidShortTurn.getError());
    	//System.out.println("PID Error: " + pidTurn90.getError());
    	//System.out.println("sideStick value: " + sideStick);
    	Robot.driveSubsystem.setDriveForward(0, sideStick);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// TODO figure out why this is broken
        //return pidTurn.onTarget();
    	double want = Robot.driveSubsystem.getAutomaticHeading();
    	double got = Robot.driveSubsystem.getAngle();
    	double error = DriveSubsystem.angleDifference(want, got);
    	logger.info("want {}, got {}, error {}, ontarget {}, getAvgError {}, getError {}", want, got, error, pidShortTurn.onTarget(),
    			pidShortTurn.getAvgError(),
    			pidShortTurn.getError());
    	
    	return error < 4;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	pidShortTurn.disable();
    	Robot.driveSubsystem.stopMotors();
    	RobotMap.driveSubsystemLeftDriveEncoder.reset();
    	RobotMap.driveSubsystemRightDriveEncoder.reset();
    	logger.info("AutomatedTurn end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    public void pidWrite(double output) {
       sideStick = output;
    }

   
}
