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
	
	public static final int driveTrainMotorLeftFront	= 8;
	public static final int driveTrainMotorLeftRear = 9;
	public static final int driveTrainMotorRightFront = 6;
	public static final int driveTrainMotorRightRear = 7;
	
	public static final int intakeMotorLeft = 11;
	public static final int intakeMotorRight = 0;
	
	public static final int PCMOne = 21;
	
	// Double Solenoids (?)
	public static final int driveTrainShifterLeft = 0;
	public static final int driveTrainShifterRight = 1;
	
	public static final int gearIntakePistonOne = 2;
	public static final int gearIntakePistonTwo = 3;
	
	public static final int gearClampPistionOne = 6;
	public static final int gearClampPistionTwo = 7;
	
	// Single Solenoids
	public static final int gearFunnelPiston = 3;
	
	public static final int hopperWall = 5;
	
}
