package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;

public class ControlPanelWatcher {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	Timer timer = new Timer();
	Joystick controPanel = Robot.oi.controlPanel;
	
	public ControlPanelWatcher() {
		timer.schedule(new MyTimerTask(), 0, 2000);
	}
	
	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			boolean controlPnaelPresent = controPanel.getRawButton(12);
			if (controlPnaelPresent) {
			  int controlPanelChoice = readControlPanel();
			  int chooserChoice = readAutonomousPicker();
			  if (controlPanelChoice != chooserChoice) {
				  // update chooser here
			  }
			}
		}
	}
	
	int readControlPanel() {
		int rv = 0;
		for (int i = 1; i < 4; i++) {
			rv = rv << 1;
			if (controPanel.getRawButton(i)) rv += 1;
		}
		return rv;
	}
	
	int readAutonomousPicker() {
		List<String> choices = Robot.autoChooser.getChoiceNames();
		String selected = Robot.autoChooser.getSelectedName();
		int rv = choices.indexOf(selected);
		return rv;
	}

}
