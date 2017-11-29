package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnWithGyro extends Command {
	double timeout, speed, turn;
	Timer stopwatch;
	double kP = 0.03, angle = 0;
	
    public DriveTurnWithGyro(double time, double speed, double turn) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        requires(Robot.utilities);
        
        this.timeout = time;
        this.turn = turn;
        this.speed = speed;
        stopwatch = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//stopwatch.start();
    	Robot.utilities.XRSGyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = Robot.utilities.XRSGyro.getAngle();
    	Robot.driveTrain.drive(-speed, (angle+turn)*kP);	//check sign to make sure it continues to drive straight
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(angle == turn) {
    		stopwatch.start();
    		return stopwatch.get() > timeout;
    	}
    	return false;
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
