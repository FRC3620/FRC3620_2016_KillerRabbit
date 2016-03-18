package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.DummyAutoPointSenecaLane2;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.DummyAutoPointSenecaLane3;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.DummyAutoPointSenecaLane4;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.DummyAutoPointSenecaLane5;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.DummyAutoShootFromSeneca;

import java.util.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SuperDuperAutonomous extends CommandGroup {
	public static Command make(Command startCommand, int lane) {
		SuperDuperAutonomous superDuper = new SuperDuperAutonomous();
		superDuper.addSequential(startCommand);
		superDuper.commands.add(startCommand);
		Command middleCommand = null;
		if (lane == 2) {
			middleCommand = new DummyAutoPointSenecaLane2();
		} else if (lane == 3) {
			middleCommand = new DummyAutoPointSenecaLane3();
		} else if (lane == 4) {
			middleCommand = new DummyAutoPointSenecaLane4();
		} else if (lane == 5) {
			middleCommand = new DummyAutoPointSenecaLane5();
		}
		superDuper.addSequential(middleCommand);
		superDuper.commands.add(middleCommand);
		superDuper.addSequential(new DummyAutoShootFromSeneca());
		return superDuper;
	}
	
	List<Command> commands = new ArrayList<>();
	
	@Override
	public String toString() {
		return "SuperDuperAutonomousMaker [commands=" + commands + "]";
	}

}
