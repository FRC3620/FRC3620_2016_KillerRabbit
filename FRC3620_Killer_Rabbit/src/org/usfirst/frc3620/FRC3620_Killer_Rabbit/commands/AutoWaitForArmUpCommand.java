package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoWaitForArmUpCommand extends Command {

	
	private final CANTalon armCANTalon = RobotMap.armSubsystemArmCANTalon;
	
    public AutoWaitForArmUpCommand() {
    	
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	
    	if(armCANTalon.getPosition() <= Robot.armSubsystem.topSetPoint + Robot.armSubsystem.cushion)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
