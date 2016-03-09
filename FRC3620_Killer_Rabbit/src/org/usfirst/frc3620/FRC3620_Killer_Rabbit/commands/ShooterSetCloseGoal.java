package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

public class ShooterSetCloseGoal extends ShooterSetPositionCommand {

	@Override
	double getDesiredVeds() {
		return edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("closeGoalVeds", 1.884);
		//1.884 position and 9.0 voltage working for competiton bot
	}

}
