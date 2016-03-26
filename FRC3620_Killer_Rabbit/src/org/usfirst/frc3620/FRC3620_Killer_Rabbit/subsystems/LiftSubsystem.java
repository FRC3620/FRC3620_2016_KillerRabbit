package org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	private final SpeedController climberMotor = RobotMap.liftSubsystemClimberMotor;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void moveClimberUp(){
		climberMotor.set(.5);
	}
	
	public void moveClimberDown(){
		climberMotor.set(-.5);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

