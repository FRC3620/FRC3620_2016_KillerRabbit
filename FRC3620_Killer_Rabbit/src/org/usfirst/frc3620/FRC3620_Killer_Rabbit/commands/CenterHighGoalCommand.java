package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.DriveSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 *
 */
public class CenterHighGoalCommand extends Command implements PIDOutput, PIDSource{

Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	double sideStick;

	double whereToTurnTo = 200; //set this to the center of the image. 200 is just a guess
	
	PIDController pidCenterHighGoal = new PIDController(.020, .0005, .00, .00, this, this); //cant use ahrs need to ask what to use
	//PIDController pidShortTurn = new PIDController(.030, .0001, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	//PIDController pidShortTurn = new PIDController(.045, .0001, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	//PIDController pidShortTurn = new PIDController(.035, .0001, .00, .00, Robot.driveSubsystem.getAhrs(), this);
	
	NetworkTable visionTable;
	
    public CenterHighGoalCommand() // the current center of the blob
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	visionTable = NetworkTable.getTable("RoboRealms");
    	pidCenterHighGoal.setInputRange(0.0f,  400f); //set this to the width of the picture. 400 is just a guess
    	pidCenterHighGoal.setOutputRange(-1, 1);
    	pidCenterHighGoal.setContinuous(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	logger.info("CenterHighGoalCommand start");
		double currentCenter = visionTable.getNumber("Blob_center_X", -1);
    	logger.info("Center of blob was {}, desiredCenter is {}", currentCenter, whereToTurnTo);
    	// TODO We need to look at this
    	pidCenterHighGoal.setSetpoint(whereToTurnTo);
    	logger.info("we rechecked the setpoint = {}", pidCenterHighGoal.getSetpoint());
    	pidCenterHighGoal.reset();
    	pidCenterHighGoal.setAbsoluteTolerance(10.0);
    	pidCenterHighGoal.enable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	SmartDashboard.putNumber("Turn P", pidCenterHighGoal.getP());
    	SmartDashboard.putNumber("Turn I", pidCenterHighGoal.getI());
    	SmartDashboard.putNumber("Turn D", pidCenterHighGoal.getD());
    	
    	SmartDashboard.putNumber("PID Turn Sidestick", sideStick);
    	SmartDashboard.putNumber("PID Angle Setpoint", pidCenterHighGoal.getSetpoint());
    	SmartDashboard.putNumber("PID Short Angle Error", pidCenterHighGoal.getError());
    	//System.out.println("PID Error: " + pidTurn90.getError());
    	//System.out.println("sideStick value: " + sideStick);
      	if(visionTable.getNumber("Blob_center_X") > 200)
    	{
      		Robot.driveSubsystem.setDriveForward(0, -0.5);
    	}
    	else
    	{
    		Robot.driveSubsystem.setDriveForward(0, 0.5);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// TODO figure out why this is broken
        //return pidTurn.onTarget();
    	double want = Robot.driveSubsystem.getAutomaticHeading();
    	double got = Robot.driveSubsystem.getAngle();
    	double error = DriveSubsystem.angleDifference(want, got);
    	logger.info("want {}, got {}, error {}, ontarget {}, getAvgError {}, getError {}", want, got, error, pidCenterHighGoal.onTarget(),
    			pidCenterHighGoal.getAvgError(),
    			pidCenterHighGoal.getError());
    	
    	return error < 4;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("CenterHighGoalCommand end");
    	pidCenterHighGoal.disable();
    	Robot.driveSubsystem.stopMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Center High Goal Command Interrupted");
    	end();
    }
    
    public void pidWrite(double output) {
       sideStick = output;
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return visionTable.getNumber("Blob_center_X");
	}
}
