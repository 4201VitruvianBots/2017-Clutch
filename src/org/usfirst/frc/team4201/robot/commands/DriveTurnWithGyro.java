package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnWithGyro extends Command {
	double speed, setpoint;
	Timer stopwatch;
	double kP = 0.1, kI = 0.0001, angleGet = 0, error = 0, output = 0;
	boolean lock = false;
	
    public DriveTurnWithGyro(double speed, double turn) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        requires(Robot.utilities);
        
        this.setpoint = turn;
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
    	angleGet = Robot.utilities.XRSGyro.getAngle();
    	
    	error = setpoint - angleGet;
    	output = error*kP;
    	Robot.driveTrain.drive(-speed, output);	//check sign to make sure it continues to drive straight
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Angle Delta: ", Math.abs(setpoint - angleGet));
    	SmartDashboard.putNumber("Stopwatch", stopwatch.get());
    	SmartDashboard.putBoolean("Lock Value: ", lock);
    	
    	if(Math.abs(setpoint - angleGet) < 0.1 && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if(Math.abs(setpoint - angleGet) >= 0.1 && lock){ // When you are outside of range && you are locked
    		stopwatch.stop();
    		stopwatch.reset();
    		lock = false;
    	}
    	
    	return stopwatch.get() > 1000;
    }

    // Called once after isFinished returns true
    protected void end() {
    	stopwatch.stop();
    	Robot.driveTrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
