package org.usfirst.frc.team4201.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static boolean manualShiftOverride = true;
	public static boolean cheesyDriveBrakeMode = true;
	
	public static final int driveTrainMotorLeftFront = 8;
	public static final int driveTrainMotorLeftRear = 9;
	public static final int driveTrainMotorRightFront = 6;
	public static final int driveTrainMotorRightRear = 7;
	
	public static final int gearIntakeMotor = 0;
	public static final int ballIntakeMotor = 11;	
	
	public static final int conveyorMotor = 10;
	public static final int shooterUptake = 4;	// Master to CANTalon 4?
	
	public static final int flywheelMaster = 5; // This is controlling the uptake
	public static final int flywheelSlave = 1; // Following CANTalon 1
	
	
	public static final int PCMOne = 21;
	
	// Double Solenoids (?)
	public static final int driveTrainShifterForward = 1;
	public static final int driveTrainShifterReverse = 0;
	
	public static final int gearClampPistionForward = 4;
	public static final int gearClampPistionReverse = 5;
	
	public static final int gearIntakePistonForward = 3;
	public static final int gearIntakePistonReverse = 2;
	
	public static final int hopperWallForward = 7;
	public static final int hopperWallReverse = 6;
}
