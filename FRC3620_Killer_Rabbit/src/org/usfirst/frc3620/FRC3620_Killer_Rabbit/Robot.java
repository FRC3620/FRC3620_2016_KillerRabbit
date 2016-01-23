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

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.File;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.EventLogging.Level;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.*;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Command autonomousCommand;
	SendableChooser autoChooser;
	PowerDistributionPanel powerDistributionPanel;
	DataLogger dataLogger;

	static Logger logger;
	
	static AHRS ahrs;

	public static OI oi;
	
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveSubsystem driveSubsystem;
    public static ShooterSubsystem shooterSubsystem;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		logger = EventLogging.getLogger(Robot.class, Level.DEBUG);
		setupLogging();
		logger.info("Starting robotInit");
		RobotMap.init();
		logger.debug("debug");
		logger.warn("warn");
		logger.error("error");
		logger.info("info");

		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveSubsystem = new DriveSubsystem();
        shooterSubsystem = new ShooterSubsystem();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		// instantiate the command used for the autonomous period
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

       
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		powerDistributionPanel = new PowerDistributionPanel();
		
		ahrs = new AHRS(SPI.Port.kMXP);
		
		autoChooser = new SendableChooser();
        autoChooser.addDefault ("Default Program" , new Autonomous1());
        autoChooser.addObject("Experimental Autonomous" , new Autonomous2());
        SmartDashboard.putData("Autonomous mode chooser", autoChooser);
	}
		
		public void autonomousInit(){
			autonomousCommand = (Command) autoChooser.getSelected();
			((Command) autonomousCommand).start();
 		}
		public void autonomousPeriodic (){
			Scheduler.getInstance().run();
		}
	

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	/**
	 * This function is called periodically during autonomous
	 */


	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			((Command) autonomousCommand).cancel();
		
		logger.warn("NaxX firmware = {}", ahrs.getFirmwareVersion());
		logger.warn("NaxX connected = {}", ahrs.isConnected());
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	void setupLogging() {
		// Set dataLogger and Time information
		TimeZone.setDefault(TimeZone.getTimeZone("America/Detroit"));

		File logDirectory = null;
		if (logDirectory == null)
			logDirectory = findLogDirectory(new File("/u"));
		if (logDirectory == null)
			logDirectory = findLogDirectory(new File("/v"));
		if (logDirectory == null)
			logDirectory = findLogDirectory(new File("/x"));
		if (logDirectory == null)
			logDirectory = findLogDirectory(new File("/y"));
		if (logDirectory == null) {
			logDirectory = new File("/home/lvuser/logs");
			if (!logDirectory.exists()) {
				logDirectory.mkdir();
			}
		}
		if (logDirectory != null && logDirectory.isDirectory()) {
			String logMessage = String.format("Log directory is %s\n",
					logDirectory);
			System.out.print(logMessage);
			EventLogging.writeToDS(logMessage);
			EventLogging.setup(logDirectory);
			dataLogger = new DataLogger(logDirectory);
			dataLogger.setMinimumInterval(100);
		}
	}

	public File findLogDirectory(File root) {
		// does the root directory exist?
		if (!root.isDirectory())
			return null;

		File logDirectory = new File(root, "logs");
		if (!logDirectory.isDirectory())
			return null;

		return logDirectory;
	}
}
