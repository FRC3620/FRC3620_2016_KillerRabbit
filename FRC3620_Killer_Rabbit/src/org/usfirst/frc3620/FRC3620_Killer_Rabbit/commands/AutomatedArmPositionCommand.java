package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.ArmSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutomatedArmPositionCommand extends Command {
	
	double whatSetpointWeWant = 0;
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	private final CANTalon armCANTalon = RobotMap.armSubsystemArmCANTalon;
	
	void moveArmToSetpoint(double position, double p, double i, double d) 
		{
			logger.info("flipping into automatic");
			armCANTalon.changeControlMode(TalonControlMode.Position);
			
		}

    public AutomatedArmPositionCommand(double setpoint) {
    	requires(Robot.armSubsystem);
    	
    whatSetpointWeWant = setpoint;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	moveArmToSetpoint(whatSetpointWeWant, 0, 0, 0);
    	
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
    	end();
    }
}
