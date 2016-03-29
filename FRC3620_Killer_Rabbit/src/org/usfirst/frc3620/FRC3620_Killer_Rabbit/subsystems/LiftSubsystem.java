package org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	  private final SpeedController liftSubsystemLiftTalon = RobotMap.liftSubsystemLiftTalon;
	  
	  public void climberUp() {
		  liftSubsystemLiftTalon.set(.85);
	  }
	  
	  public void climberDown() {
		  liftSubsystemLiftTalon.set(-.85);
	  }
	  
	  public void climberStop() {
		  liftSubsystemLiftTalon.set(0);
	  }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

