package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.DriveSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.usfirst.frc3620.logger.IDataLoggerDataProvider;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class RobotDataLoggerDataProvider implements IDataLoggerDataProvider {
    Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
    boolean pdbIsPresent = false;
    boolean armTalonIsPresent = false;

    DriveSubsystem driveSubsystem = Robot.driveSubsystem;

    PowerDistributionPanel powerDistributionPanel = Robot.powerDistributionPanel;
    CANTalon armTalon = RobotMap.armSubsystemArmCANTalon;
    DriverStation driverStation = DriverStation.getInstance();

    Timer timer = new Timer();

    public RobotDataLoggerDataProvider() {
        super();
        timer.reset();
        timer.start();

        pdbIsPresent = Robot.canDeviceFinder.isPDPPresent();
        armTalonIsPresent = Robot.canDeviceFinder.isSRXPresent(armTalon);
        logger.info("PDP present = {}, armTalon present = {}", pdbIsPresent,
                armTalonIsPresent);
    }

    @Override
    public String[] fetchNames() {
        return new String[] { //
                "robotMode", //
                "robotModeInt", //

                "batteryVoltage", //
                "totalCurrent", //
                "totalPower", //
                "totalEnergy", //

                "lf.power", //
                "lr.power", //
                "rf.power", //
                "rr.power", //

                "lf.current", //
                "lr.current", //
                "rf.current", //
                "rr.current", //

                "armTalon.error", //
                "armTalon.current", //
                "armTalon.voltage", //
                "armTalon.mode", //

                "drive.automaticHeading", //
                "drive.angle", //
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
                pdbIsPresent ? f2(powerDistributionPanel.getTotalCurrent()) : 0, //
                pdbIsPresent ? f2(powerDistributionPanel.getTotalPower()) : 0, //
                pdbIsPresent ? f2(powerDistributionPanel.getTotalEnergy()) : 0, //

                f2(RobotMap.driveSubsystemLeftFront.get()), //
                f2(RobotMap.driveSubsystemLeftRear.get()), //
                f2(RobotMap.driveSubsystemRightFront.get()), //
                f2(RobotMap.driveSubsystemRightRear.get()), //

                pdbIsPresent ? f2(powerDistributionPanel.getCurrent(12)) : 0, //
                pdbIsPresent ? f2(powerDistributionPanel.getCurrent(13)) : 0, //
                pdbIsPresent ? f2(powerDistributionPanel.getCurrent(14)) : 0, //
                pdbIsPresent ? f2(powerDistributionPanel.getCurrent(15)) : 0, //

                armTalonIsPresent ? f2(armTalon.getClosedLoopError()) : 0, //
                armTalonIsPresent ? f2(armTalon.getOutputCurrent()) : 0, //
                armTalonIsPresent ? f2(armTalon.getOutputVoltage()) : 0, //
                armTalonIsPresent ? armTalon.getControlMode().toString() : "", //

                f2(driveSubsystem.getAutomaticHeading()), //
                f2(driveSubsystem.getAngle()), //

        };
    }

    DecimalFormat f2Formatter = new DecimalFormat("#.##");

    String f2(double f) {
        String rv = f2Formatter.format(f);
        return f2Formatter.format(f);
    }

}
