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
	Joystick controlPanel = Robot.oi.controlPanel;
	AverageSendableChooser autonomousChooser = Robot.autoChooser;

	public ControlPanelWatcher() {
		// look at the control panel every 2000 ms (2 seconds)
		timer.schedule(new MyTimerTask(), 0, 2000);
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			boolean controlPanelPresent = controlPanel.getRawButton(11) || controlPanel.getRawButton(12);
			List<String> chooserNames = autonomousChooser.getChoiceNames();
			String chooserSelectedName = autonomousChooser.getSelectedName();
			int chooserIndex = chooserNames.indexOf(chooserSelectedName);

			if (controlPanelPresent) {
				// the control panel is connected to the driver's station
				int controlPanelIndex = readControlPanel();

				if (controlPanelIndex != chooserIndex) {
					if (controlPanelIndex < chooserNames.size()) {
						String controlPanelName = chooserNames.get(controlPanelIndex);
						logger.info("control panel says {}, the chooser says {}, updating chooser to {}",
								controlPanelIndex, chooserIndex, controlPanelName);

						// update chooser here
						autonomousChooser.select(controlPanelName);
						chooserSelectedName = controlPanelName;
					} else {
						logger.info("control panel says {}, don't have that many", controlPanelIndex);
					}
				}
			}

			String preferencesName = Robot.preferences.getString("autonomous", chooserNames.get(0));
			if (!chooserSelectedName.equals(preferencesName)) {
				logger.info("changing autonomous in preferences from {} to {}", preferencesName, chooserSelectedName);
				Robot.preferences.putString("autonomous", chooserSelectedName);
			}
		}
	}

	int readControlPanel() {
		int rv = 0;
		for (int i = 3; i > 0; i--) {
			rv = rv << 1;
			if (controlPanel.getRawButton(i))
				rv += 1;
		}
		return rv;
	}

}
