package org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands;

public class ShooterSetCloseGoal extends ShooterSetPositionCommand {

	@Override
	double getDesiredVeds() {
		return edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("closeGoalVeds", 1.88);
	}

}
