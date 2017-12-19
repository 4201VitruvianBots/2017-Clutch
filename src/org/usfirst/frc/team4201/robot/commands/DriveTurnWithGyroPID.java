package org.usfirst.frc.team4201.robot.commands;
import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurnWithGyroPID extends PIDCommand{
    static double kP = 0.1;        		// Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0.001;           // Start with I = P / 100
    static double kD = 0;           	// Start with D = P * 10
    static double period = 0.01;
    
    double throttle, setpoint, getAngle;
    Timer stopwatch;
    boolean lock = false;
    
    public DriveTurnWithGyroPID(double speed, double angle){
        super("DriveTurnWithGyroPID", kP, kI, kD, period);
        getPIDController().setContinuous(true);
        getPIDController().setAbsoluteTolerance(0.1);
        getPIDController().setOutputRange(-1, 1); // Is this okay, or does this need to be an angle to match gyro output?
        
        this.throttle = speed;
        this.setpoint = angle;
    }
    
    @Override
    protected double returnPIDInput() {
        // TODO Auto-generated method stub
    	getAngle = Robot.utilities.XRSGyro.getAngle();
        return getAngle;
    }
    
    @Override
    protected void usePIDOutput(double output) {
        // TODO Auto-generated method stub
        SmartDashboard.putNumber("PID Output", output); // is this an angle value, or a percentage? Output may need to be negative
        Robot.driveTrain.drive(throttle, output);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        // Use this to test/tune PID loop through smartDashboard
    	//SmartDashboard.putNumber("kP", kP);
        //SmartDashboard.putNumber("kI", kI);
        //SmartDashboard.putNumber("kD", kD);
    	
        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);
        
        stopwatch = new Timer();
        getPIDController().enable();
        getPIDController().setSetpoint(setpoint);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    @Override
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Stopwatch", stopwatch.get());
    	SmartDashboard.putBoolean("Lock Value: ", lock);
    	
    	if(Math.abs(setpoint - getAngle) < 0.1 && !lock) { // When you are in range && you are not locked
    		stopwatch.start();
    		lock = true;
    	} else if(Math.abs(setpoint - getAngle) >= 0.1 && lock){ // When you are outside of range && you are locked
    		stopwatch.stop();
    		stopwatch.reset();
    		lock = false;
    	}
    	
    	boolean finished = stopwatch.get() > 1; 
    	SmartDashboard.putBoolean("Finished Turn", finished);
    	return finished;
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