package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WaitAndExtendClimberCommand extends CommandGroup {
    
    public  WaitAndExtendClimberCommand() {
    	       
        	addSequential(new AutonomousDoNothingCommand(), 0);
        	addSequential(new RunClimberCommand(), 4.5);
        	
        }
        
    public WaitAndExtendClimberCommand(double howLong) {
        	addSequential(new AutonomousDoNothingCommand(), howLong);
        	addSequential(new RunClimberCommand(), 4.5);
        	
        }
    	
}
