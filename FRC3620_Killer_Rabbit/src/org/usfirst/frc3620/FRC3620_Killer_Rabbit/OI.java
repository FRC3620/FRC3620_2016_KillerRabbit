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

import org.usfirst.frc3620.FRC3620_Killer_Rabbit.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc3620.FRC3620_Killer_Rabbit.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton shootBall;
    public JoystickButton outtake;
    public JoystickButton forceIntakeButton;
    public Joystick operatorJoystick;
    public JoystickButton setDriveToFront;
    public JoystickButton setDriveToBack;
    public JoystickButton switchCamera;
    public JoystickButton toggleShooterButton;
    public Joystick driverJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton intakeButton;
    
    public Button armUpButton;
    public Button armDownButton;

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(1);
        // 4 = Y button
        toggleShooterButton = new JoystickButton(operatorJoystick, 4);
        toggleShooterButton.toggleWhenPressed(new RunShooterCommand());
        
        switchCamera = new JoystickButton(driverJoystick, 9);
        switchCamera.whenPressed(new SwitchCameraCommand());
        setDriveToBack = new JoystickButton(driverJoystick, 5);
        setDriveToBack.whenPressed(new SetDriveToRearCommand());
        setDriveToFront = new JoystickButton(driverJoystick, 6);
        setDriveToFront.whenPressed(new SetDriveToFrontCommand());
       
        
        forceIntakeButton = new JoystickButton(operatorJoystick, 3);
        forceIntakeButton.whileHeld(new ForceIntakeCommand());
        outtake = new JoystickButton(operatorJoystick, 5);
        outtake.whenPressed(new OuttakeCommand());
        shootBall = new JoystickButton(operatorJoystick, 2);
        shootBall.whenPressed(new ShootBallCommand());


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous1Command", new Autonomous1Command());
        SmartDashboard.putData("Autonomous2Command", new Autonomous2Command());
        SmartDashboard.putData("ArmUpCommand", new ArmUpCommand());
        SmartDashboard.putData("ArmLowerCommand", new ArmLowerCommand());
        SmartDashboard.putData("ShootBallCommand", new ShootBallCommand());
        SmartDashboard.putData("IntakeCommand", new IntakeCommand());
        SmartDashboard.putData("OuttakeCommand", new OuttakeCommand());
        SmartDashboard.putData("SetDriveToFrontCommand", new SetDriveToFrontCommand());
        SmartDashboard.putData("SetDriveToRearCommand", new SetDriveToRearCommand());
        SmartDashboard.putData("SwitchCameraCommand", new SwitchCameraCommand());
        SmartDashboard.putData("ArmManualCommand", new ArmManualCommand());
        SmartDashboard.putData("ForceIntakeCommand", new ForceIntakeCommand());
        SmartDashboard.putData("ToggleShooterCommand", new RunShooterCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        intakeButton = new JoystickButton(operatorJoystick, 1);
        //intakeButton.whenPressed(new IntakeCommand());
        intakeButton.toggleWhenPressed(new IntakeCommand());
        
       

        armUpButton = new DPadUpButton(operatorJoystick);
        armUpButton.whenPressed(new ArmUpCommand());
        armDownButton = new DPadDownButton(operatorJoystick);
        armDownButton.whenPressed(new ArmLowerCommand());

        SmartDashboard.putData("DougsAutonomous", new DougsAutonomousCommand());
        SmartDashboard.putData("AutonomousCDF", new AutonomousCDF());
        SmartDashboard.putData("AutomatedRight90", new AutomatedTurnCommand(90.0));
        SmartDashboard.putData("AutomatedRight45", new AutomatedTurnCommand(45.0));
        SmartDashboard.putData("AutomatedLeft90", new AutomatedTurnCommand(-90.0));
        SmartDashboard.putData("AutomatedLeft45", new AutomatedTurnCommand(-45.0));
        SmartDashboard.putData("Move5Feet(.75)", new AutomatedMove(60, .75));
        SmartDashboard.putData("Move5Feet(.50)", new AutomatedMove(60, .5));
        SmartDashboard.putData("Move5Feet(.25)", new AutomatedMove(60, .25));

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }

    public Joystick getDriverJoystick() {
        return driverJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

