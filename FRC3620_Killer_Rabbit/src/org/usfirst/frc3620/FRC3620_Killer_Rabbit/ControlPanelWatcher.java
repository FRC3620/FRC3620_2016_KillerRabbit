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
    AverageSendableChooser laneChooser = Robot.laneChooser;

	public ControlPanelWatcher() {
		// look at the control panel every 2000 ms (2 seconds)
		timer.schedule(new MyTimerTask(), 0, 2000);
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			boolean controlPanelPresent = controlPanel.getAxis(AxisType.kZ) > 0.95;
			logger.debug("The z axis is {}", controlPanel.getAxis(AxisType.kZ));
			List<String> autonomousChooserNames = autonomousChooser.getChoiceNames();
			String autonomousChooserSelectedName = autonomousChooser.getSelectedName();
			int autonomousChooserIndex = autonomousChooserNames.indexOf(autonomousChooserSelectedName);
            List<String> laneChooserNames = laneChooser.getChoiceNames();
            String laneChooserSelectedName = laneChooser.getSelectedName();
            int laneChooserIndex = laneChooserNames.indexOf(laneChooserSelectedName);

			if (controlPanelPresent) {
				// the control panel is connected to the driver's station
				int controlPanelAutonomousIndex = readAutonomousFromControlPanel();
				logger.debug("Control panel autonomous is {}", controlPanelAutonomousIndex);

				if (controlPanelAutonomousIndex != autonomousChooserIndex) {
					if (controlPanelAutonomousIndex < autonomousChooserNames.size()) {
						String controlPanelName = autonomousChooserNames.get(controlPanelAutonomousIndex);
						logger.info("control panel says autonomous {}, the chooser says {}, updating chooser to {}",
								controlPanelAutonomousIndex, autonomousChooserIndex, controlPanelName);

						// update chooser here
						autonomousChooser.select(controlPanelName);
						autonomousChooserSelectedName = controlPanelName;
					} else {
						logger.info("control panel says autonomous {}, don't have that many", controlPanelAutonomousIndex);
					}
				}

				int controlPanelLaneIndex = readLaneFromControlPanel();
                logger.debug("Control panel lane is {}", controlPanelLaneIndex);

                if (controlPanelLaneIndex != laneChooserIndex) {
                    if (controlPanelLaneIndex < laneChooserNames.size()) {
                        String controlPanelName = laneChooserNames.get(controlPanelLaneIndex);
                        logger.info("control panel says lane {}, the chooser says {}, updating chooser to {}",
                                controlPanelLaneIndex, laneChooserIndex, controlPanelName);

                        // update chooser here
                        laneChooser.select(controlPanelName);
                        laneChooserSelectedName = controlPanelName;
                    } else {
                        logger.info("control panel says lane {}, don't have that many", controlPanelLaneIndex);
                    }
                }
			}

			String autonomousPreferencesName = Robot.preferences.getString("autonomous", autonomousChooserNames.get(0));
			if (!autonomousChooserSelectedName.equals(autonomousPreferencesName)) {
				logger.info("changing autonomous in preferences from {} to {}", autonomousPreferencesName, autonomousChooserSelectedName);
				Robot.preferences.putString("autonomous", autonomousChooserSelectedName);
			}
            String lanePreferencesName = Robot.preferences.getString("autonomousLane", laneChooserNames.get(0));
            if (!laneChooserSelectedName.equals(lanePreferencesName)) {
                logger.info("changing autonomous lane in preferences from {} to {}", lanePreferencesName, laneChooserSelectedName);
                Robot.preferences.putString("autonomousLane", laneChooserSelectedName);
            }
		}
	}

	int readAutonomousFromControlPanel() {
		int rv = 0;
		// these bits are 7, 8, 9, 10 on the Arduino end
		for (int i = 11; i >= 8; i--) {
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
        // these bits are 4, 5, 6 on the Arduino end
        for (int i = 7; i >= 5; i--) {
            rv = rv << 1;
            boolean b = controlPanel.getRawButton(i);
            if (b)
                rv += 1;
            logger.debug("button {} = {}, lane is now {}", i, b, rv);
        }
        return rv;
    }

}
