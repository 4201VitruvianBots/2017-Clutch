package org.usfirst.frc.team4201.robot.commands;
import org.usfirst.frc.team4201.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignToVisionTarget extends PIDCommand{
    static double kP = 0.1;
    static double kI = 0.002;
    static double kD = 0;
    static double period = 0.01;
    
    double throttle, setpoint, angleGet;
    double[] centerX;
    double cameraCenterX = 160;         // Center of camera in the X axis, in pixels
    double anglesPerPixel = 0.17125; 	// 320x240 w/ 68.5 FOV // 0.085625 - 640x480 w/ 68.5 FOV
    NetworkTable visionTable;
    
    Timer stopwatch;
    boolean lock = false;
    
    public AlignToVisionTarget(){
        super("AlignToVisionTarget", kP, kI, kD, period);
        getPIDController().setContinuous(true);
        getPIDController().setAbsoluteTolerance(.1);
        getPIDController().setOutputRange(-1, 1);
        
    }
    @Override
    protected double returnPIDInput() {
        // TODO Auto-generated method stub
        angleGet = Robot.utilities.XRSGyro.getAngle();
        return angleGet;
    }
    @Override
    protected void usePIDOutput(double output) {
        // TODO Auto-generated method stub
        SmartDashboard.putNumber("PID Output", output); 
        Robot.driveTrain.drive(throttle, output); // Convert output angle to angular power
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	stopwatch = new Timer();
    	
        try{    // In case of no VP data
            visionTable = NetworkTable.getTable("/vision");
            centerX = visionTable.getNumberArray("centerX", new double[] {});  // CenterX is an array to account for multiple targets detected
            this.setpoint = (centerX[0] - cameraCenterX) * anglesPerPixel; //CenterX[0] assuming only 1 target, need a better implementation for multiple targets
            getPIDController().enable();
            getPIDController().setSetpoint(setpoint);
        } catch(Exception e){
            DriverStation.reportWarning("4201-Warning:" + e, false);
        }
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // We repeat this in execute to update the setpoint for accuracy
        try{
            centerX = visionTable.getNumberArray("centerX", new double[] {}); 
            this.setpoint = (centerX[0] - cameraCenterX) * anglesPerPixel;
            getPIDController().setSetpoint(setpoint);
        } catch(Exception e){
            DriverStation.reportWarning("4201-Warning:" + e, false);
        }
    }
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
    	try{
	    	if(Math.abs(setpoint - angleGet) < 0.1 && !lock) { // When you are in range && you are not locked
	    		stopwatch.start();
	    		lock = true;
	    	} else if(Math.abs(setpoint - angleGet) >= 0.1 && lock){ // When you are outside of range && you are locked
	    		stopwatch.stop();
	    		stopwatch.reset();
	    		lock = false;
	    	}
	    	
	    	return stopwatch.get() > 1;
    	}
    	catch(Exception e){
            DriverStation.reportWarning("4201-Warning:" + e, false);
    		return false;
    	}
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