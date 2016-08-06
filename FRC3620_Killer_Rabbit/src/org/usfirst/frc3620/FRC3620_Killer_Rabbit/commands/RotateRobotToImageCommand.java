package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RotateRobotToImageCommand extends Command implements PIDSource, PIDOutput {

	
	
	double sideStick;
	
	double kP;
	double kI;
	double kD;
	
	PIDController pidRotateRobotToImage = new PIDController(kP, kI, kD, 0.0, this, this);
	
    public RotateRobotToImageCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	
    	
    	
    	pidRotateRobotToImage.setInputRange(0.0f, 1024.0f);
    	pidRotateRobotToImage.setOutputRange(-.75, .75);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	kP = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("VisionP Value", .05);
    	kI = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("VisionI Value", 0);
    	kD = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("VisionD Value", 0);
    	pidRotateRobotToImage.setPID(kP, kI, kD);
    	
    	pidRotateRobotToImage.reset();
    	pidRotateRobotToImage.setAbsoluteTolerance(20.0);
    	pidRotateRobotToImage.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Vision P", pidRotateRobotToImage.getP());
    	SmartDashboard.putNumber("Vision I", pidRotateRobotToImage.getI());
    	SmartDashboard.putNumber("Vision D", pidRotateRobotToImage.getD());
    	SmartDashboard.putNumber("PID Vision Sidestick", sideStick);
    	SmartDashboard.putNumber("PID Vision Input", pidGet());
    	
    	Robot.driveSubsystem.setDriveForward(0, -sideStick);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if(Math.abs(Robot.driveSubsystem.getTargetCenter())<20){
    		Robot.driveSubsystem.setAutomaticHeading(Robot.driveSubsystem.getAngle());
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	pidRotateRobotToImage.disable();
    	Robot.driveSubsystem.stopMotors();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    public void pidWrite(double output){
    	if(Robot.driveSubsystem.isBlobThere()){
    	sideStick=output;
    	}
    	else{
    		sideStick = 0.0;
    	}
    }
    
    public void setPIDSourceType(PIDSourceType pidSource){
    	
    }
    
    public PIDSourceType getPIDSourceType(){
    	return PIDSourceType.kDisplacement;
    }
    
    public double pidGet(){
    	return Robot.driveSubsystem.getTargetCenter();
    }
}
