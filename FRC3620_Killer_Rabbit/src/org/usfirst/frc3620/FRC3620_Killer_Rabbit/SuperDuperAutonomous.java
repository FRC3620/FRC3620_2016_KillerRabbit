package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoPointSenecaLane2;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoPointSenecaLane2AndAHalf;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoPointSenecaLane3;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoPointSenecaLane4;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoPointSenecaLane5;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoPointSenecaLane5AndAHalf;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.AutoShootFromSeneca;

import java.util.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SuperDuperAutonomous extends CommandGroup {
	public static Command make(Command startCommand, String lane) {
		SuperDuperAutonomous superDuper = new SuperDuperAutonomous();
		superDuper.addSequential(startCommand);
		superDuper.commands.add(startCommand);
		Command middleCommand = null;
		if (lane.equalsIgnoreCase("2L")) {
            middleCommand = new AutoPointSenecaLane2AndAHalf();
		}else if (lane.equalsIgnoreCase("2")) {
            middleCommand = new AutoPointSenecaLane2();
		}else if (lane.equalsIgnoreCase("3")) {
			middleCommand = new AutoPointSenecaLane3();
        }else if (lane.equalsIgnoreCase("4")) {
			middleCommand = new AutoPointSenecaLane4();
        }else if (lane.equalsIgnoreCase("5")) {
			middleCommand = new AutoPointSenecaLane5();
        }else if (lane.equalsIgnoreCase("5R")) {
			middleCommand = new AutoPointSenecaLane5AndAHalf();
		} 
		
		if (middleCommand != null) {
		  superDuper.addSequential(middleCommand);
		  superDuper.commands.add(middleCommand);
		  superDuper.addSequential(new AutoShootFromSeneca());
		}
		return superDuper;
	}
	
	List<Command> commands = new ArrayList<>();
	
	@Override
	public String toString() {
		return "SuperDuperAutonomousMaker [commands=" + commands + "]";
	}

}
