package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousPortcullis extends CommandGroup {
    
    public  AutonomousPortcullis() {
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
    	
    	
    	//AutomatedMove overshoots about 6"
    	//addSequential(new AutomatedMove(37, .65));
    	addSequential(new ResetNavXCommand());
    	addSequential(new AutomatedMove(40, .65));
    	addSequential(new ArmLowerCommand());
    	addSequential(new AutoWaitForArmDownCommand());
    	addSequential(new AutomatedMoveTimed(2, .58));
    	addSequential(new ArmUpCommand());
    	addSequential(new AutomatedMove(96, .65));
    }
}
