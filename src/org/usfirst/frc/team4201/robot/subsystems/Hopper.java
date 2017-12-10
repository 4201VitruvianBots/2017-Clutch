package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Hopper extends Subsystem {
	// Check if hopper wall is single or double
	DoubleSolenoid hopperWall = new DoubleSolenoid(RobotMap.PCMOne, RobotMap.hopperWallForward, RobotMap.hopperWallReverse);
	
	public Hopper() {
		super();
		
	}
	
	public void hopperExtend() {
		hopperWall.set(Value.kForward);
	}
	
	public void hopperRetract() {
		hopperWall.set(Value.kReverse);
	}
	
	public Value getHopperStatus() {
		return hopperWall.get();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Hopper Wall Status", getHopperStatus() == Value.kForward ? true : false);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

