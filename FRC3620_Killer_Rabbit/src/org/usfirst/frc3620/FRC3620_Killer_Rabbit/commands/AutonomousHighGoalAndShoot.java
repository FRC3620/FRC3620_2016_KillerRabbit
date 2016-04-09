package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousHighGoalAndShoot extends CommandGroup {
    
    public  AutonomousHighGoalAndShoot() {
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
    	addParallel(new ShooterSetHome(), 1);
    	addSequential(new AutomatedMove(30, .65));
    	addSequential(new ArmLowerCommand());
    	addSequential(new AutoWaitForArmDownCommand());
    	addSequential(new AutomatedMove(50, .7));
    	addSequential(new ArmUpCommand());
    	
    	// 2016-04-09 1146 add some distance
    	//addSequential(new AutomatedMove(85,.7));
    	addSequential(new AutomatedMove(109,.7));

    	// 2016-04-09 1146 add timeout
    	addSequential(new AutomatedShortTurnCommand(52), 2.0);
    	
    	addSequential(new ShooterSetCloseGoal(), 1.5);
    	addSequential(new AutomatedMove(122, .65));
    	addParallel(new AutomatedMoveTimed(5, .80));
    	addParallel(new AutoRunShooterCommand());
    	addParallel(new AutoWaitAndShoot());
    	addSequential(new AutoStopShooterCommand());
    
    }
}
