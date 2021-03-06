package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.ShooterSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
abstract public class ShooterSetPositionCommand extends Command implements PIDSource, PIDOutput {

	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	PIDController shooterPositionPID = new PIDController(1 ,0 , 0,this, this);
	
    public ShooterSetPositionCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires (Robot.shooterSubsystem);
    	shooterPositionPID.setOutputRange(-.5, .5);
    }
    
    abstract double getDesiredVeds();

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    		double kP = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ShooterBasketP Value", 5);
    		double kI = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ShooterBasketI Value", 0);
    		double kD = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ShooterBasketD Value", 0);
    		logger.info("shooterpid p={} i={} d={}", kP, kI, kD);
    		
    		
    		shooterPositionPID.setPID(kP, kI, kD);
    		shooterPositionPID.setAbsoluteTolerance(.01);
    		
    		double desiredVeds = getDesiredVeds();
    		logger.info("setting shooter tilt setpoint, desired veds = {}", desiredVeds);
    		shooterPositionPID.setSetpoint(desiredVeds);
    		shooterPositionPID.reset();
    		// onTarget is busted
        	//shooterPositionPID.setAbsoluteTolerance(5);
    		shooterPositionPID.enable();
    		

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		/* commented out 3-5-16 for testing
    		 * 
    		 
    		double error = shooterPositionPID.getError();
    		
    		// onTarget is busted. figure that out someday
    		
    		boolean onTarget = shooterPositionPID.onTarget();
    		double avgError = shooterPositionPID.getAvgError();
    		SmartDashboard.putNumber("ShooterTiltAvgError", avgError);
    		SmartDashboard.putNumber("ShooterTiltError", error);
    		SmartDashboard.putBoolean("ShooterTiltOnTarget", onTarget);
    		
    		return Math.abs(error) < .00075;
    		*/
    	return Robot.oi.operatorJoystick.getRawAxis(3) > .3 || Robot.oi.operatorJoystick.getRawAxis(2) > .3 ;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	shooterPositionPID.disable();
    	Robot.shooterSubsystem.stopShooterPositionTalon();
    	logger.info("Shoot Position command ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	public void pidWrite(double output) {
		Robot.shooterSubsystem.setMoveTilt(-output);
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
		return Robot.shooterSubsystem.getTiltPotentiometerPostion();
		
	}
}
