package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GroundGearIntake extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon intakeMotor = new CANTalon(RobotMap.gearIntakeMotor);
	//DoubleSolenoid gearIntakePistons = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearIntakePistonForward, RobotMap.gearIntakePistonReverse);
	DoubleSolenoid gearClampPistons = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearClampPistionForward, RobotMap.gearClampPistionReverse);
	DoubleSolenoid gearIntakePistons = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.gearIntakePistonForward, RobotMap.gearIntakePistonReverse);
	
	public GroundGearIntake() {
		super();
		intakeMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void deployIntake() {
		gearIntakePistons.set(Value.kForward);
	}
	
	public void retractIntake() {
		gearIntakePistons.set(Value.kReverse);
	}
	
	public Value getIntakeStatus() {
		return gearIntakePistons.get();
	}
	
	public void activateGearIntakeMotors() {
		intakeMotor.set(-0.8);
	}
	
	public void deactivateGearIntakeMotors() {
		intakeMotor.set(0);
	}
	
	public void reverseGearIntakeMotors() {
		intakeMotor.set(0.8);
	}
	
	public void openGearClamp() {
		gearClampPistons.set(Value.kForward);
	}

	public void closeGearClamp() {
		gearClampPistons.set(Value.kReverse);
	}
	
	public Value getIntakeClampStatus() {
		return gearClampPistons.get();
	}
	
	public void updateSmartDashboard() {
		//SmartDashboard.putBoolean("Intake Deployed", getIntakeStatus() == Value.kForward ? true : false);
		SmartDashboard.putBoolean("Intake Deployed", getIntakeStatus() == Value.kForward ? true : false);
		SmartDashboard.putBoolean("Gear Clamps Open", getIntakeClampStatus() == Value.kForward ? true : false);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

