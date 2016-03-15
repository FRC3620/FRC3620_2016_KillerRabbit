// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.Robot;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMap;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.RobotMode;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	//static final double bottomSetPoint = 0.7;
	public static final double defaultBottomSetPoint = 1.15;
	public static final double defaultTopSetPoint = 0.0;
	public static final double cushion = 0.1;
	static final double creepPower = 0.25;
	
	Timer timer = new Timer();

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final CANTalon armCANTalon = RobotMap.armSubsystemArmCANTalon;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public ArmSubsystem() {
		super();

		double kP = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ArmP Value", .4);
		double kI = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ArmI Value", 0);
		double kD = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ArmD Value", 0);
		logger.info("armpid p={} i={} d={}", kP, kI, kD);

		
		// TODO Auto-generated constructor stub
		// armCANTalon.changeControlMode(TalonControlMode.Position);
		weAreInManualMode = true;
		armCANTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		armCANTalon.enableBrakeMode(true);
		// SIMs need reverseSensor(true), BAGs need false.
		armCANTalon.reverseSensor(true);
		//armCANTalon.setPID(0.4, .00001, 0.01); hitting robot too hard
		armCANTalon.setPID(kP, kI, kD);
		armCANTalon.setPosition(getArmTopSetPoint());
		// logger.info("encoder position is " + armCANTalon.getEncPosition());
		// armCANTalon.setEncPosition(0);
		// logger.info("now the encoder position is " +
		// armCANTalon.getEncPosition());
		moveManually(0);

		//Check limit switch for homing.
		if (RobotMap.armSubsystemHomeDigitalInput.get() == false) {
			//Arm is homed, encoder valid.
			encoderIsValid = true;
			armCANTalon.setPosition(0);
		}
		else {
			//Arm not home, set for homing.
			RobotMap.armSubsystemHomeDigitalInput.requestInterrupts(new MyHandler());
			RobotMap.armSubsystemHomeDigitalInput.setUpSourceEdge(false, true);
			RobotMap.armSubsystemHomeDigitalInput.enableInterrupts();	
		}
		logger.info("encoder is valid = {} ", encoderIsValid);
	}
	
	class MyHandler extends InterruptHandlerFunction<Void> {

		@Override
		public void interruptFired(int interruptAssertedMask, Void param) {
			logger.info("Interrupt happened.");
			if (encoderIsValid == false) {
				armCANTalon.setPosition(0);
				encoderIsValid = true;
				logger.info("Encoder is now valid.");
				RobotMap.armSubsystemHomeDigitalInput.disableInterrupts();
			}
		}
    	
    }

	public boolean getEncoderIsValid() {
		return encoderIsValid;
	}
	
	public boolean isArmUp() {
		double armPosition = armCANTalon.getPosition();
		logger.info("Arm CanTalon position {}", armPosition);
		if (armPosition > .1) 
			return false;
		else
		return true;
	}

	boolean weAreInManualMode = false;
	boolean encoderIsValid = false;

	// Put methods for controlling this subsystem here. Call these from
	// Commands.

	/*
	 * this gets called from Robot.java when we transition from mode to mode
	 * (disabled -> teleop, teleop -> disabled, etc)
	 */
	public void allInit(RobotMode newRobotMode) {
		
		if (newRobotMode == RobotMode.TELEOP) {
			logger.info("Armsubsystem sees we are going into Teleop, setting vbusmode");
			moveManually(0);
			
			double kP = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ArmP Value", .4);
			double kI = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ArmI Value", 0);
			double kD = edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("ArmD Value", 0);
			logger.info("armpid p={} i={} d={}", kP, kI, kD);
			
			armCANTalon.setPID(kP, kI, kD);
		}
	}

	public void moveArmToTop() {
		moveArmToSetpoint(getArmTopSetPoint(), 0, 0, 0);
		if(RobotMap.armSubsystemHomeDigitalInput.get())
		{
			timer.start();
			if (timer.get() < 0.2) {
				Robot.armSubsystem.nudgeToTop();
			}
			else {
				Robot.armSubsystem.stopNudging();
				timer.stop();
				timer.reset();
			}
		}
	}

	public void moveArmToBottom() {
		moveArmToSetpoint(getArmBottomSetPoint(), 0, 0, 0);
	}
	
	
	public double getArmBottomSetPoint() {
		return edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("armBottomSetPoint", defaultBottomSetPoint);
	}
	
	public double getArmTopSetPoint() {
		return edu.wpi.first.wpilibj.Preferences.getInstance().getDouble("armTopSetPoint", defaultTopSetPoint);
	}

	void moveArmToSetpoint(double position, double p, double i, double d) {
		if (encoderIsValid) {
			
		if (weAreInManualMode) {
			logger.info("flipping into automatic");
			armCANTalon.changeControlMode(TalonControlMode.Position);
			weAreInManualMode = false;
		}
		// TODO set PID from p, i, d
		armCANTalon.setSetpoint(position);
		logger.info("setting setpoint = " + position);
		}
		else {
			logger.info("Ignoring moveArmToSetpoint because encoder isn't valid");
		}
	}

	public void goIntoAutomaticMode() {
		if (encoderIsValid) {
			if (weAreInManualMode) {
				logger.info("flipping into automatic");
				armCANTalon.changeControlMode(TalonControlMode.Position);
				weAreInManualMode = false;

				// Makes sure the new setpoint stays between the low and high
				// setpoints.
				double encoderPosition = armCANTalon.getPosition();
				double desiredSetpoint = encoderPosition;
				desiredSetpoint = Math.min(getArmBottomSetPoint(), desiredSetpoint);
				desiredSetpoint = Math.max(getArmTopSetPoint(), desiredSetpoint);
				armCANTalon.setSetpoint(desiredSetpoint);
				logger.info("encoder position is " + encoderPosition);
				logger.info("setting setpoint to " + armCANTalon.getSetpoint());
			}
		} else {
			logger.warn("Cannot enable position mode: encoder invalid");
		}
	}

	public void moveManually(double directionAndSpeed) {
		if (Robot.canDeviceFinder.isSRXPresent(armCANTalon)) {
			// Positive direction and speed moves us towards smaller setpoints.
			// Smaller setpoints are up.

			if (!weAreInManualMode) {
				logger.info("flipping into manual");
				armCANTalon.changeControlMode(TalonControlMode.PercentVbus);
				weAreInManualMode = true;

			}
			double adjustedPower = 0;
			double position = armCANTalon.getPosition();
			if (encoderIsValid) {
				if (directionAndSpeed > 0) {
					// moves up toward a smaller top setpoint.
					if (position < getArmTopSetPoint()) {
						adjustedPower = 0;
					} else if (position < getArmTopSetPoint() + cushion) {
						adjustedPower = Math.min(creepPower, directionAndSpeed);
					} else {
						adjustedPower = directionAndSpeed;
					}
				} else {
					double bottomSetPoint = getArmBottomSetPoint();
					if (position > bottomSetPoint) {
						adjustedPower = 0;
					} else if (position > bottomSetPoint - cushion) {
						adjustedPower = Math.max(-creepPower,
								directionAndSpeed);
					} else {
						adjustedPower = directionAndSpeed;
					}
				}
			} else {
				if (directionAndSpeed > 0) {
					// moves up toward a smaller top setpoint.
					adjustedPower = Math.min(creepPower, directionAndSpeed);
				} else {
					adjustedPower = Math.max(-creepPower, directionAndSpeed);
				}
			}
			// if direction and speed are positive, it should move up.
			// Therefore,
			// negative power.
			armCANTalon.set(-adjustedPower);
			if (adjustedPower != directionAndSpeed) {
				logger.info("arm power {} adjusted to {}", directionAndSpeed,
						adjustedPower);
			}
		}
	}
	
	public void stopNudging(){
		armCANTalon.set(0);
	}
	
	public void nudgeToTop() {
		armCANTalon.set(-creepPower);
		
	}
	
	
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		setDefaultCommand(new ArmManualCommand());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public boolean areWeInManual()
	{
		return weAreInManualMode;
	}
}
