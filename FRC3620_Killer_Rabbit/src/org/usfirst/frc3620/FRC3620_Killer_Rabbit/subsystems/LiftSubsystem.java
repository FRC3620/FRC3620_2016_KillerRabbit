package org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.ControllerRunClimber;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	  private final SpeedController liftSubsystemLiftTalon = RobotMap.liftSubsystemLiftTalon;
	  
	  double climberPower = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("Climber Power", 1);
	  
	  public void climberUp() {
		  liftSubsystemLiftTalon.set(climberPower);
	  }
	  
	  public void climberDown() {
		  liftSubsystemLiftTalon.set(-climberPower);
	  }
	  
	  public void climberStop() {
		  liftSubsystemLiftTalon.set(0);
	  }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ControllerRunClimber());
    }
}

