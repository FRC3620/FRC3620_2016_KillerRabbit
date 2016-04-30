package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.DriveSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.usfirst.frc3620.logger.IDataLoggerDataProvider;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class RobotDataLoggerDataProvider implements IDataLoggerDataProvider {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	boolean pdpIsPresent = false;
	boolean armTalonIsPresent = false;
	boolean shooterTalonsArePresent = false;

	DriveSubsystem driveSubsystem = Robot.driveSubsystem;

	PowerDistributionPanel powerDistributionPanel = Robot.powerDistributionPanel;
	CANTalon armTalon = RobotMap.armSubsystemArmCANTalon;
	CANTalon shooterTalon2 = RobotMap.shooterSubsystemShooterCANTalon2;
	CANTalon shooterTalon3 = RobotMap.shooterSubsystemShooterCANTalon3;
	DriverStation driverStation = DriverStation.getInstance();
	
	BuiltInAccelerometer builtinAccelerometer = new BuiltInAccelerometer();

	Timer timer = new Timer();

	public RobotDataLoggerDataProvider() {
		super();
		timer.reset();
		timer.start();

		pdpIsPresent = Robot.canDeviceFinder.isPDPPresent();
		armTalonIsPresent = Robot.canDeviceFinder.isSRXPresent(armTalon);
		shooterTalonsArePresent = Robot.canDeviceFinder
				.isSRXPresent(shooterTalon2);
		logger.info("PDP present = {}, armTalon present = {}", pdpIsPresent,
				armTalonIsPresent);
	}

	@Override
	public String[] fetchNames() {
		return new String[] { //
				"robotMode", //
				"robotModeInt", //

				"batteryVoltage", //
				"pdp.totalCurrent", //
				"pdp.totalPower", //
				"pdp.totalEnergy", //

				"drive.lf.power", //
				"drive.lr.power", //
				"drive.rf.power", //
				"drive.rr.power", //

				"drive.lf.current", //
				"drive.lr.current", //
				"drive.rf.current", //
				"drive.rr.current", //

				"armTalon.error", //
				"armTalon.current", //
				"armTalon.voltage", //
				"armTalon.mode", //

				"shooter.2.v", //
				"shooter.2.a", //
				"shooter.3.v", //
				"shooter.3.a", //

				"drive.automaticHeading", //
				"drive.angle", //
				"drive.pitchangle", //
				"drive.rollangle", //

                "drive.accel.x", //
                "drive.accel.y", //
                "drive.accel.z", //

                "bi.accel.x", //
                "bi.accel.y", //
                "bi.accel.z", //
                
                "lift.power",
                "lift.current",

		};
	}

	@Override
	public Object[] fetchData() {
		boolean shouldSample = true;
		if (Robot.getCurrentRobotMode() != RobotMode.TELEOP
				&& Robot.getCurrentRobotMode() != RobotMode.AUTONOMOUS) {
			shouldSample = timer.hasPeriodPassed(1.0);
		}

		if (!shouldSample)
			return null;

		return new Object[] { //
				Robot.currentRobotMode.toString(), //
				Robot.currentRobotMode.ordinal(), //

				f2(driverStation.getBatteryVoltage()), //
				pdpIsPresent ? f2(powerDistributionPanel.getTotalCurrent()) : 0, //
				pdpIsPresent ? f2(powerDistributionPanel.getTotalPower()) : 0, //
				pdpIsPresent ? f2(powerDistributionPanel.getTotalEnergy()) : 0, //

				f2(RobotMap.driveSubsystemLeftFront.get()), //
				f2(RobotMap.driveSubsystemLeftRear.get()), //
				f2(RobotMap.driveSubsystemRightFront.get()), //
				f2(RobotMap.driveSubsystemRightRear.get()), //

				pdpIsPresent ? f2(powerDistributionPanel.getCurrent(12)) : 0, //
				pdpIsPresent ? f2(powerDistributionPanel.getCurrent(13)) : 0, //
				pdpIsPresent ? f2(powerDistributionPanel.getCurrent(14)) : 0, //
				pdpIsPresent ? f2(powerDistributionPanel.getCurrent(15)) : 0, //

				armTalonIsPresent ? f2(armTalon.getClosedLoopError()) : 0, //
				armTalonIsPresent ? f2(armTalon.getOutputCurrent()) : 0, //
				armTalonIsPresent ? f2(armTalon.getOutputVoltage()) : 0, //
				armTalonIsPresent ? armTalon.getControlMode().toString() : "", //

				shooterTalonsArePresent ? f2(shooterTalon2.getOutputVoltage()) : 0, //
				shooterTalonsArePresent ? f2(shooterTalon2.getOutputCurrent()) : 0, //
				shooterTalonsArePresent ? f2(shooterTalon3.getOutputVoltage()) : 0, //
				shooterTalonsArePresent ? f2(shooterTalon3.getOutputCurrent()) : 0, //

				f2(driveSubsystem.getAutomaticHeading()), //
				f2(driveSubsystem.getAngle()), //
				f2(driveSubsystem.getPitch()), //
				f2(driveSubsystem.getRoll()), //

                f2(driveSubsystem.getAccelX()), //
                f2(driveSubsystem.getAccelY()), //
                f2(driveSubsystem.getAccelZ()), //

                f2(builtinAccelerometer.getX()), //
                f2(builtinAccelerometer.getY()), //
                f2(builtinAccelerometer.getZ()), //
                
				pdpIsPresent ? f2(powerDistributionPanel.getCurrent(0)) : 0, //
			    f2(RobotMap.liftSubsystemLiftTalon.get()),

		};
	}

	DecimalFormat f2Formatter = new DecimalFormat("#.##");

	String f2(double f) {
		String rv = f2Formatter.format(f);
		return rv;
	}

}
