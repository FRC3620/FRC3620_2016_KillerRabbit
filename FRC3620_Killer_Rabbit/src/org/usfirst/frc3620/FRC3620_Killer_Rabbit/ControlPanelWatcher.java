package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

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
			boolean controlPanelPresent = controlPanel.getAxis(AxisType.kZ) > 0.95;
			logger.debug("The z axis is {}", controlPanel.getAxis(AxisType.kZ));
			List<String> chooserNames = autonomousChooser.getChoiceNames();
			String chooserSelectedName = autonomousChooser.getSelectedName();
			int chooserIndex = chooserNames.indexOf(chooserSelectedName);

			if (controlPanelPresent) {
				// the control panel is connected to the driver's station
				int controlPanelIndex = readAutonomousFromControlPanel();
				logger.debug("Control panel is {}", controlPanelIndex);

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

	int readAutonomousFromControlPanel() {
		int rv = 0;
		// these bits are 8, 9, 10 on the Arduino end
		for (int i = 11; i >= 9; i--) {
			rv = rv << 1;
			boolean b = controlPanel.getRawButton(i);
			if (b)
				rv += 1;
			logger.debug("button {} = {}, autonomous is now {}", i, b, rv);
		}
		return rv;
	}

    int readLaneFromControlPanel() {
        int rv = 0;
        // these bits are 5, 6, 7 on the Arduino end
        for (int i = 8; i >= 6; i--) {
            rv = rv << 1;
            boolean b = controlPanel.getRawButton(i);
            if (b)
                rv += 1;
            logger.debug("button {} = {}, lane is now {}", i, b, rv);
        }
        return rv;
    }

}
