package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPadLeftButton extends Button {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	Joystick joystick;

	public DPadLeftButton(Joystick _joystick) {
		super();
		joystick = _joystick;
	}

	@Override
	public boolean get() {
		int angle = joystick.getPOV(0);

		boolean pressed = false;

		// Is the DPad pushed?
		if (angle > -1) {
			if (angle > 224 && angle < 316) {
				pressed = true;
			}
		}
		// logger.info("POV = {}, pressed (up) = {}", angle, pressed);
		return pressed;
	}

}
