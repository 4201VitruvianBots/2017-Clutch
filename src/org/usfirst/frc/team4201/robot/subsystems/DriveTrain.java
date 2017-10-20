package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.SetSplitArcade;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	CANTalon[] leftMotors = {
		new CANTalon(RobotMap.driveTrainMotorLeftFront),
		new CANTalon(RobotMap.driveTrainMotorLeftRear),
	};
	CANTalon[] rightMotors = {
		new CANTalon(RobotMap.driveTrainMotorRightFront),
		new CANTalon(RobotMap.driveTrainMotorRightRear),
	};
	RobotDrive robotDrive = new RobotDrive(leftMotors[0], leftMotors[1], rightMotors[0], rightMotors[1]);
	
	DoubleSolenoid[] driveTrainShifters = {
		new DoubleSolenoid(RobotMap.PCMOne, RobotMap.driveTrainShifterLeft),
		new DoubleSolenoid(RobotMap.PCMOne, RobotMap.driveTrainShifterRight)
	};
	
	double upperShiftThreshold = 12;
	double lowerShiftThreshold = 10;
	
	public DriveTrain() {
		super();

		leftMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		leftMotors[1].changeControlMode(TalonControlMode.Follower);
		leftMotors[1].set(leftMotors[0].getDeviceID());
        rightMotors[0].changeControlMode(TalonControlMode.PercentVbus);
        rightMotors[1].changeControlMode(TalonControlMode.Follower);
        rightMotors[1].set(rightMotors[0].getDeviceID());
	}
	
	void initDriverControl() {
		// Clear sticky faults to avoid errors
		leftMotors[0].clearStickyFaults();
		leftMotors[1].clearStickyFaults();
        rightMotors[0].clearStickyFaults();
        rightMotors[1].clearStickyFaults();
        
        // Set Control Mode to ensure proper control mode
		leftMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		leftMotors[1].changeControlMode(TalonControlMode.PercentVbus);
        rightMotors[0].changeControlMode(TalonControlMode.PercentVbus);
        rightMotors[1].changeControlMode(TalonControlMode.PercentVbus);
		
        // Ensure motors are not inverted
        robotDrive.setInvertedMotor(MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(MotorType.kFrontRight, false);
        robotDrive.setInvertedMotor(MotorType.kRearLeft, false);
        robotDrive.setInvertedMotor(MotorType.kRearRight, false);
	}
	
	public void tankDrive(Joystick leftJoystick, Joystick rightJoystick) {
		initDriverControl();
        
        // Set Tank Drive with cubed inputs
    	robotDrive.tankDrive(Math.pow(leftJoystick.getAxis(AxisType.kY), 3), Math.pow(rightJoystick.getAxis(AxisType.kY), 3));
	}
	
	public void splitArcadeDrive(Joystick leftJoystick, Joystick rightJoystick) {
		initDriverControl();
        
        // Set split Arcade Drive with squared inputs
    	robotDrive.arcadeDrive(leftJoystick.getAxis(AxisType.kY), rightJoystick.getAxis(AxisType.kX), true); 
	}
	
	public void setHighGear() {
		driveTrainShifters[0].set(Value.kForward);
		driveTrainShifters[1].set(Value.kForward);
	}
	
	public void setLowGear() {
		driveTrainShifters[0].set(Value.kReverse);
		driveTrainShifters[1].set(Value.kReverse);
	}
	
	public Value getShiftStatus() {
		// Assuming both shifters will always be in the same position
		return driveTrainShifters[0].get();
	}
	
	/** This is a model, need to get values before implementing.
	 * 
	 *	<p> Automatically shift between low/high gear using pre-defined thresholds. 
	 *	Thresholds are separate to avoid gearbox damage if you hover between a single threshold.
	 */
	public void autoGearShifting() {
		double encoderValue = 11;
		
		if(!RobotMap.manualShiftOverride)
			if(encoderValue > upperShiftThreshold)
				setHighGear();
			else if(encoderValue < lowerShiftThreshold)
				setLowGear();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("High Gear", driveTrainShifters[0].get() == Value.kForward ? true : false);
		SmartDashboard.putBoolean("Auto Shifting", !RobotMap.manualShiftOverride);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new SetSplitArcade());
	}
}
