package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import org.slf4j.Logger;
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

                "armTalon.error", //
                "armTalon.current", //
                "armTalon.voltage", //
                "armTalon.mode", //
        };
    }

    @Override
    public Object[] fetchData() {
        boolean shouldSample = true;
        if (Robot.getCurrentRobotMode() != RobotMode.TELEOP && Robot.getCurrentRobotMode() != RobotMode.AUTONOMOUS) {
            shouldSample = timer.hasPeriodPassed(1.0);
        }
                
        return new Object[] { //
                Robot.currentRobotMode.toString(), //
                Robot.currentRobotMode.ordinal(), //

                driverStation.getBatteryVoltage(), //
                pdbIsPresent ? powerDistributionPanel.getTotalCurrent() : 0, //
                pdbIsPresent ? powerDistributionPanel.getTotalPower() : 0, //
                pdbIsPresent ? powerDistributionPanel.getTotalEnergy() : 0, //

                armTalonIsPresent ? armTalon.getClosedLoopError() : 0, //
                armTalonIsPresent ? armTalon.getOutputCurrent() : 0, //
                armTalonIsPresent ? armTalon.getOutputVoltage() : 0, //
                armTalonIsPresent ? armTalon.getControlMode().toString() : "", //

        };
    }

}
