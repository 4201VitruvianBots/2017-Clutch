package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4201.robot.Robot;

/**
 *
 */
public class SetCheesyDrive extends Command {
	public SetCheesyDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.setLowGear();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
    	Robot.driveTrain.cheesyDrive(Robot.oi.leftJoystick, Robot.oi.rightJoystick);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
