package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCDF2 extends CommandGroup {
    
    public  AutonomousCDF2() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new ResetNavXCommand());
    	addSequential(new AutomatedMove(50, .65));
    	addSequential(new ArmLowerCommand());
    	addSequential(new AutoWaitForArmDownCommand(), 1);
    	addSequential(new AutomatedMove(.5, -.60), .5);
    	addSequential(new AutomatedMove(36, .80));
    	addSequential(new ArmUpCommand());
    	addSequential(new AutomatedMove(40, .75));
    	
    }
}
