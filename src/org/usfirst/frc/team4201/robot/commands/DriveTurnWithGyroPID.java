package org.usfirst.frc.team4201.robot.commands;
import org.usfirst.frc.team4201.robot.Robot;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurnWithGyroPID extends PIDCommand{
    static double kP = 0.1;         // Start with P = 10% of your max output, double until you get a quarter-decay oscillation
    static double kI = 0;           // Start with I = P / 100
    static double kD = 0;           // Start with D = P * 10
    static double period = 0.01;
    
    double throttle, setpoint;
    DriveTurnWithGyroPID(double speed, double angle){
        super("DriveTurnWithGyroPID", kP, kI, kD, period);
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(0.1);
        getPIDController().setOutputRange(-1, 1); // Is this okay, or does this need to be an angle to match gyro output?
        
        this.throttle = speed;
        this.setpoint = angle;
    }
    @Override
    protected double returnPIDInput() {
        // TODO Auto-generated method stub
        return Robot.utilities.XRSGyro.getAngle();
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
        kP = SmartDashboard.getNumber("kP", 0.1);
        kI = SmartDashboard.getNumber("kI", 0);
        kD = SmartDashboard.getNumber("kD", 0);
        
        getPIDController().enable();
        getPIDController().setSetpoint(setpoint);
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
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