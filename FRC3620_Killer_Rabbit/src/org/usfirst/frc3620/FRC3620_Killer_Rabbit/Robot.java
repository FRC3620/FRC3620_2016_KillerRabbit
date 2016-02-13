// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3620.FRC3620_Killer_Rabbit;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.slf4j.Logger;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.*;
import org.usfirst.frc3620.logger.DataLogger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	static RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode;

	Command autonomousCommand;
	SendableChooser autoChooser;
	PowerDistributionPanel powerDistributionPanel;

	DataLogger dataLogger;

	static Logger logger;
	
	static public AHRS ahrs;
	
	

	public static OI oi;
	
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveSubsystem driveSubsystem;
    public static ShooterSubsystem shooterSubsystem;
    public static ArmSubsystem armSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static DummySubsystem dummySubsystem;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		logger = EventLogging.getLogger(Robot.class, Level.INFO);
		
		dataLogger = new DataLogger();
		
		logger.info("Starting robotInit");
		RobotMap.init();
		logger.debug("debug");
		logger.warn("warn");
		logger.error("error");
		logger.info("info");

		ahrs = new AHRS(SPI.Port.kMXP);
		
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveSubsystem = new DriveSubsystem();
        shooterSubsystem = new ShooterSubsystem();
        armSubsystem = new ArmSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        dummySubsystem = new DummySubsystem();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		// instantiate the command used for the autonomous period
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        autonomousCommand = new AutonomousCommand();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		powerDistributionPanel = new PowerDistributionPanel();
		
		
		
		autoChooser = new SendableChooser();
        autoChooser.addDefault ("Default Program" , new Autonomous1());
        autoChooser.addObject("Experimental Autonomous" , new Autonomous2());
        SmartDashboard.putData("Autonomous mode chooser", autoChooser);
      
	}
		
	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	
	public void disabledInit() {
		allInit(RobotMode.DISABLED);
	}

	public void disabledPeriodic() {
		beginAllPeriodic();
		Scheduler.getInstance().run();
		endAllPeriodic();
	}

	public void autonomousInit() {
		allInit(RobotMode.AUTONOMOUS);
		
		
		
		driveSubsystem.resetNavX();
		
		autonomousCommand = (Command) autoChooser.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		beginAllPeriodic();
		Scheduler.getInstance().run();
		endAllPeriodic();
	}

	public void teleopInit() {
		allInit(RobotMode.TELEOP);
		
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			((Command) autonomousCommand).cancel();
		
		logger.info("NaxX firmware = {}", ahrs.getFirmwareVersion());
		logger.info("NaxX connected = {}", ahrs.isConnected());
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		beginAllPeriodic();
		Scheduler.getInstance().run();
		endAllPeriodic();
	}
	
	public void testInit() {
		allInit(RobotMode.TEST);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		beginAllPeriodic();
		LiveWindow.run();
		endAllPeriodic();
	}
	
	/*
	 * this routine gets called whenever we change modes
	 */
	void allInit (RobotMode newMode)
	{
		logger.info("Switching from {} to {}", currentRobotMode, newMode);
		previousRobotMode = currentRobotMode;
		currentRobotMode = newMode;
		
		// if any subsystems need to know about mode changes, let
		// them know here.
		Robot.driveSubsystem.allInit(newMode);
	}
	
	
	/*
	 * these routines get called at the beginning and end of all periodics.
	 */
	void beginAllPeriodic() {
		// don't need to do anything
		
	}
	
	void endAllPeriodic() {
		// if some subsystems to get called in all modes at the beginning
		// of periodic, do it here

		
		// and log data!
		updateDashboard();
		logData();
	}
	
	/*
	 * these methods take care of logging to the dashboard or the datalog file. 
	 */
	
	void updateDashboard() {
		SmartDashboard.putNumber("NavX Angle",ahrs.getAngle());
		SmartDashboard.putNumber("DriveLeftEncoder", RobotMap.driveSubsystemLeftDriveEncoder.getDistance());
		SmartDashboard.putNumber("DriveRightEncoder", RobotMap.driveSubsystemRightDriveEncoder.getDistance());
/*		SmartDashboard.putNumber("Battery voltage", powerDistributionPanel.getVoltage());
		logger.info("v = {}", powerDistributionPanel.getVoltage());
		logger.info("e = {}", powerDistributionPanel.getTotalEnergy());
		logger.info("p = {}", powerDistributionPanel.getTotalPower());
*/	
		}
	
	void logData() {
		if (dataLogger.shouldLogData()) {
/*			dataLogger.addDataItem("battery.voltage", powerDistributionPanel.getVoltage());
			dataLogger.saveDataItems();
*/		}
	}
	
	/*
	 * subsystems can use these to find out what mode we are in
	 */
	public static RobotMode getCurrentRobotMode() {
		return currentRobotMode;
	}
	
	public static RobotMode getPreviousRobotMode() {
		return previousRobotMode;
	}
	
}
