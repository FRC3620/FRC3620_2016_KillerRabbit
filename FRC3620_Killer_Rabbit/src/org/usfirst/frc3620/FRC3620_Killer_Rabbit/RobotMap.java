

package org.usfirst.frc3620.FRC3620_Killer_Rabbit;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
    public static SpeedController driveSubsystemLeftFront;
    public static SpeedController driveSubsystemLeftRear;
    public static SpeedController driveSubsystemRightFront;
    public static SpeedController driveSubsystemRightRear;
    public static RobotDrive driveSubsystemRobotDrive41;
    public static Encoder driveSubsystemLeftDriveEncoder;
    public static Encoder driveSubsystemRightDriveEncoder;
    public static CANTalon shooterSubsystemShooterCANTalon2;
    public static CANTalon shooterSubsystemShooterCANTalon3;
    public static SpeedController shooterSubsystemShooterPositionTalon;
    public static CANTalon armSubsystemArmCANTalon;
    public static AnalogInput intakeSubsystemBallSensorAnalogInput;
    public static Encoder intakeSubsystemIntakeRollerEncoder;
    public static SpeedController intakeSubsystemIntakeRollerTalonFront;
    public static SpeedController intakeSubsystemIntakeRollerTalonBack;
    public static AnalogInput dummySubsystemAnalogInput1;
    public static DigitalInput armSubsystemHomeDigitalInput;

 

	public static void init() {
		
        driveSubsystemLeftFront = new Talon(1);
        LiveWindow.addActuator("DriveSubsystem", "Left Front", (Talon) driveSubsystemLeftFront);
        
        driveSubsystemLeftRear = new Talon(2);
        LiveWindow.addActuator("DriveSubsystem", "Left Rear", (Talon) driveSubsystemLeftRear);
        
        driveSubsystemRightFront = new Talon(3);
        LiveWindow.addActuator("DriveSubsystem", "Right Front", (Talon) driveSubsystemRightFront);
        
        driveSubsystemRightRear = new Talon(4);
        LiveWindow.addActuator("DriveSubsystem", "Right Rear", (Talon) driveSubsystemRightRear);
        
        driveSubsystemRobotDrive41 = new RobotDrive(driveSubsystemLeftFront, driveSubsystemLeftRear,
              driveSubsystemRightFront, driveSubsystemRightRear);
        
        driveSubsystemRobotDrive41.setSafetyEnabled(true);
        driveSubsystemRobotDrive41.setExpiration(0.1);
        driveSubsystemRobotDrive41.setSensitivity(0.5);
        driveSubsystemRobotDrive41.setMaxOutput(1.0);
        driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveSubsystemRobotDrive41.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        
        driveSubsystemLeftDriveEncoder = new Encoder(1, 2, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveSubsystem", "LeftDriveEncoder", driveSubsystemLeftDriveEncoder);
        driveSubsystemLeftDriveEncoder.setPIDSourceType(PIDSourceType.kRate);
       
        driveSubsystemRightDriveEncoder = new Encoder(3, 4, true, EncodingType.k4X);
        LiveWindow.addSensor("DriveSubsystem", "RightDriveEncoder", driveSubsystemRightDriveEncoder);
        driveSubsystemRightDriveEncoder.setPIDSourceType(PIDSourceType.kRate);
        
        driveSubsystemLeftDriveEncoder.setDistancePerPulse(((2*Math.PI*1.841)/(128))*(42.5/48));
        driveSubsystemRightDriveEncoder.setDistancePerPulse(((2*Math.PI*1.841)/(256))*(42.5/48));

        shooterSubsystemShooterCANTalon2 = new CANTalon(2);
        LiveWindow.addActuator("ShooterSubsystem", "ShooterCANTalon2", shooterSubsystemShooterCANTalon2);
        
        shooterSubsystemShooterCANTalon3 = new CANTalon(3);
        LiveWindow.addActuator("ShooterSubsystem", "ShooterCANTalon3", shooterSubsystemShooterCANTalon3);
        
        shooterSubsystemShooterPositionTalon = new Talon(5);
        LiveWindow.addActuator("ShooterSubsystem", "ShooterPositionTalon", (Talon) shooterSubsystemShooterPositionTalon);
        
        armSubsystemArmCANTalon = new CANTalon(1);
        LiveWindow.addActuator("ArmSubsystem", "ArmCANTalon", armSubsystemArmCANTalon);
        
        intakeSubsystemBallSensorAnalogInput = new AnalogInput(0);
        LiveWindow.addSensor("IntakeSubsystem", "Ball Sensor Analog Input", intakeSubsystemBallSensorAnalogInput);
        
        intakeSubsystemIntakeRollerEncoder = new Encoder(6, 7, false, EncodingType.k4X);
        LiveWindow.addSensor("IntakeSubsystem", "Intake RollerEncoder", intakeSubsystemIntakeRollerEncoder);
        intakeSubsystemIntakeRollerEncoder.setDistancePerPulse(1.0);
        intakeSubsystemIntakeRollerEncoder.setPIDSourceType(PIDSourceType.kRate);
        intakeSubsystemIntakeRollerTalonFront = new Talon(6);
        LiveWindow.addActuator("IntakeSubsystem", "IntakeRollerTalonFront", (Talon) intakeSubsystemIntakeRollerTalonFront);
        
        intakeSubsystemIntakeRollerTalonBack = new Talon(7);
        LiveWindow.addActuator("IntakeSubsystem", "IntakeRollerTalonBack", (Talon) intakeSubsystemIntakeRollerTalonBack);
        
        dummySubsystemAnalogInput1 = new AnalogInput(1);
        LiveWindow.addSensor("DummySubsystem", "Analog Input 1", dummySubsystemAnalogInput1);
        
        armSubsystemHomeDigitalInput = new DigitalInput(5);
        LiveWindow.addSensor("ArmSubsystem", "Digital Input 5", armSubsystemHomeDigitalInput);
	}
}
