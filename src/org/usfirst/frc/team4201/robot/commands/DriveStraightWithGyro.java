package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightWithGyro extends Command {
	double timeout, speed;
	Timer stopwatch;
	double kP = 0.03;
	
    public DriveStraightWithGyro(double time, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        requires(Robot.utilities);
        
        this.timeout = time;
        this.speed = speed;
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopwatch.start();
    	Robot.utilities.XRSGyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = Robot.utilities.XRSGyro.getAngle();
    	Robot.driveTrain.drive(speed, angle*kP);	//check sign to make sure it continues to drive straight
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stopwatch.get() > timeout;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
