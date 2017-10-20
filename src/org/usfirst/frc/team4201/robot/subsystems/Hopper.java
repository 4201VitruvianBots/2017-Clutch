package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Hopper extends Subsystem {
	// Check if hopper wall is single or double
	Solenoid hopperWall = new Solenoid(RobotMap.PCMOne, RobotMap.hopperWall);
	
	public Hopper() {
		super();
		
	}
	
	public void hopperExtend() {
		hopperWall.set(true);
	}
	
	public void hopperRetract() {
		hopperWall.set(false);
	}
	
	public Boolean getHopperStatus() {
		return hopperWall.get();
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Hopper Wall Status", getHopperStatus());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

