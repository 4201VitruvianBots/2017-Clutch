package org.usfirst.frc.team4201.robot.subsystems;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Utilities extends Subsystem{
	
	public ADXRS450_Gyro XRSGyro;
	
	public Utilities(){
		super();
		//XRSGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		
	}
	
	public void updateSmartDashboard(){
		//SmartDashboard.putNumber("XRS Gyro", XRSGyro.getAngle());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
