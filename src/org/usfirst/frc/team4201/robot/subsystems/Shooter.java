package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {
	CANTalon ballIntake = new CANTalon(RobotMap.ballIntakeMotor);
	CANTalon conveyor = new CANTalon(RobotMap.conveyorMotor);
	CANTalon uptake = new CANTalon(RobotMap.shooterUptake);
	CANTalon[] shooter = {
		new CANTalon(RobotMap.flywheelMaster),	
		new CANTalon(RobotMap.flywheelSlave),
	};
	CANTalon testMotor;
	
	public Shooter(){
		super();
		ballIntake.changeControlMode(TalonControlMode.PercentVbus);
		conveyor.changeControlMode(TalonControlMode.PercentVbus);
		uptake.changeControlMode(TalonControlMode.PercentVbus);
		shooter[0].changeControlMode(TalonControlMode.Speed);
		shooter[0].setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter[0].reverseOutput(true);
		shooter[0].setProfile(0);
		shooter[0].setP(0.4);
		shooter[1].changeControlMode(TalonControlMode.Follower);
		shooter[1].set(shooter[0].getDeviceID());
	}
	
	public void enableBallIntake(){
		ballIntake.set(0.45);
	}
	
	public void disableBallIntake(){
		ballIntake.set(0);
	}
	
	public double getBallIntakeOutput(){
		return ballIntake.get();
	}
	
	public void conveyorUptakeOn(){
		conveyor.set(0.95);
		uptake.set(0.5);
	}
	
	public void conveyorUptakeOff(){
		conveyor.set(0);
		uptake.set(0);
	}
	public double getConveyorOutput(){
		return conveyor.get();
	}
	
	public void setFlywheelOutput(double RPM){
		shooter[0].enable();
		shooter[0].set(RPM);
	}
	
	public void disableFlywheel(){
		shooter[0].disable();
	}
	
	public double getFlywheelOutput(){
		return shooter[0].get();
	}
	
	public boolean getFlywheelEnable(){
		return shooter[0].isEnabled();
	}
	
	public void initTestMotor(int CANTalon){
		testMotor = new CANTalon(CANTalon);
		testMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void setTestMotorOutput(double value){
		testMotor.set(value);
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Flywheel Output", getFlywheelOutput());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

