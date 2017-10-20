package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GroundGearIntake extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon[] intakeMotors = {
		new CANTalon(RobotMap.intakeMotorLeft),
		new CANTalon(RobotMap.intakeMotorRight)	
	};
	DoubleSolenoid[] gearIntakePistons = {
		new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearIntakePistonOne),
		new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearIntakePistonTwo)
	};
	DoubleSolenoid[] gearClampPistons = {
		new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearClampPistionOne),
		new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearClampPistionTwo)
	};
	
	public GroundGearIntake() {
		super();
		intakeMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		intakeMotors[1].changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void deployIntake() {
		gearIntakePistons[0].set(Value.kForward);
		gearIntakePistons[1].set(Value.kForward);
	}
	
	public void retractIntake() {
		gearIntakePistons[0].set(Value.kReverse);
		gearIntakePistons[1].set(Value.kReverse);
	}
	
	public Value getIntakeStatus() {
		return gearIntakePistons[0].get();
	}
	
	public void activateGearIntakeMotors() {
		intakeMotors[0].set(1);
		intakeMotors[1].set(-1);
	}
	
	public void deactivateGearIntakeMotors() {
		intakeMotors[0].set(0);
		intakeMotors[1].set(0);
	}
	
	public void reverseGearIntakeMotors() {
		intakeMotors[0].set(-1);
		intakeMotors[1].set(1);
	}
	
	public void openGearClamp() {
		gearClampPistons[0].set(Value.kForward);
		gearClampPistons[1].set(Value.kForward);
	}

	public void closeGearClamp() {
		gearClampPistons[0].set(Value.kReverse);
		gearClampPistons[1].set(Value.kReverse);
	}
	
	public Value getIntakeClampStatus() {
		return gearClampPistons[0].get();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Intake Deployed", getIntakeStatus() == Value.kForward ? true : false);
		SmartDashboard.putBoolean("Gear Clamps Open", getIntakeClampStatus() == Value.kForward ? true : false);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

