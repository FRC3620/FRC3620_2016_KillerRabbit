package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoWaitAndShoot extends CommandGroup {
    
    public  AutoWaitAndShoot() {
       
    	addSequential(new AutonomousDoNothingCommand(), 2);
    	addSequential(new ShootBallCommand());
    	
    }
}
