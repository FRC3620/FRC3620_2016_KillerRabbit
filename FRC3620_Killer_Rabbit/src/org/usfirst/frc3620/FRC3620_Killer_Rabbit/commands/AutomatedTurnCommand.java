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
public class AutomatedTurnCommand extends Command implements PIDOutput{
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	double sideStick;
	
	double howFarWeWantToTurn = 0;
	
	//PIDController pidTurn = new PIDController(.025, 00, 00, kF, ahrs, this); undershot
	//PIDController pidTurn = new PIDController(.025, .001, 00, kF, ahrs, this); overshot
	//PIDController pidTurn = new PIDController(.035, .001, 00, kF, ahrs, this); overshot
	//PIDController pidTurn = new PIDController(.015, .001, 00, kF, ahrs, this); overshot
	//PIDController pidTurn = new PIDController(.015, .0001, 00, kF, ahrs, this); works
	PIDController pidTurn = new PIDController(.018, .00005, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	
	public AutomatedTurnCommand() {
		this(90.0);
	}

    public AutomatedTurnCommand(double howFar) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	
    	pidTurn.setInputRange(0.0f,  360.0f);
    	pidTurn.setOutputRange(-1, 1);
    	pidTurn.setContinuous(true);
    	
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
    	pidTurn.setSetpoint(newAngle);
    	logger.info("we rechecked the setpoint = {}", pidTurn.getSetpoint());
    	pidTurn.reset();
    	pidTurn.setAbsoluteTolerance(10.0);
    	pidTurn.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	SmartDashboard.putNumber("Turn P", pidTurn.getP());
    	SmartDashboard.putNumber("Turn I", pidTurn.getI());
    	SmartDashboard.putNumber("Turn D", pidTurn.getD());
    	
    	SmartDashboard.putNumber("PID Turn Sidestick", sideStick);
    	SmartDashboard.putNumber("PID Angle Setpoint", pidTurn.getSetpoint());
    	SmartDashboard.putNumber("PID Angle Error", pidTurn.getError());
    	//System.out.println("PID Error: " + pidTurn90.getError());
    	//System.out.println("sideStick value: " + sideStick);
    	//Robot.driveSubsystem.setDriveForward(0, sideStick);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// TODO figure out why this is broken
        //return pidTurn.onTarget();
    	double want = Robot.driveSubsystem.getAutomaticHeading();
    	double got = Robot.driveSubsystem.getAngle();
    	double error = DriveSubsystem.angleDifference(want, got);
    	logger.info("want {}, got {}, error {}, ontarget {}, getAvgError {}, getError {}", want, got, error, pidTurn.onTarget(),
    			pidTurn.getAvgError(),
    			pidTurn.getError());
    	
    	return error < 10;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("AutomatedTurn end");
    	pidTurn.disable();
    	Robot.driveSubsystem.stopMotors();
    	RobotMap.driveSubsystemLeftDriveEncoder.reset();
    	RobotMap.driveSubsystemRightDriveEncoder.reset();
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
