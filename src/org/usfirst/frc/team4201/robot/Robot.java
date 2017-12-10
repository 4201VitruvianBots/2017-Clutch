
package org.usfirst.frc.team4201.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain driveTrain;
	public static Hopper hopper;
	public static GroundGearIntake groundGearIntake;
	public static Shooter shooter;
	public static Utilities utilities;
	public static OI oi;

	Command autonomousCommand;
	Command teleOpDrive;
	SendableChooser<Command> autoModes = new SendableChooser<>();
	SendableChooser<Command> driveMode = new SendableChooser<>();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		hopper = new Hopper();
		groundGearIntake = new GroundGearIntake();
		shooter = new Shooter();
		utilities = new Utilities();
		oi = new OI();
		
		CameraServer.getInstance().startAutomaticCapture();	
		
		autoModes.addDefault("Default Auto", new DriveTurnWithGyro(0, -90));
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Automous Routines", autoModes);
		
		driveMode.addDefault("Split Arcade", new SetSplitArcade());
		driveMode.addObject("Tank Drive", new SetTankDrive());
		driveMode.addObject("Cheesy Drive", new SetCheesyDrive());
		SmartDashboard.putData("Drive Type", driveMode);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = autoModes.getSelected();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		utilities.updateSmartDashboard();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		// Set Drive Control Type on TeleOp initialization
		teleOpDrive = driveMode.getSelected();
		if (teleOpDrive != null)
			teleOpDrive.start();
		
		// Deploy Hopper Wall if undeployed
		if(hopper.getHopperStatus() != Value.kForward) {
			Command deployHopper = new ExtendHopperWall();
			deployHopper.start();
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		driveTrain.updateSmartDashboard();
		hopper.updateSmartDashboard();
		groundGearIntake.updateSmartDashboard();
		shooter.updateSmartDashboard();
		utilities.updateSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
