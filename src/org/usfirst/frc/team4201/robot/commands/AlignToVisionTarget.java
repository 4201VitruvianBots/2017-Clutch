package org.usfirst.frc.team4201.robot.commands;
import org.usfirst.frc.team4201.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AlignToVisionTarget extends PIDCommand{
    static double kP = 0.1;
    static double kI = 0;
    static double kD = 0;
    static double period = 0.01;
    
    double throttle, setpoint;
    double[] centerX;
    double cameraCenterX = 320;         // Center of camera in the X axis, in pixels
    double anglesPerPixel = 0.085625;   // 640x480 w/ 68.5 FOV
    NetworkTable visionTable;
    
    AlignToVisionTarget(){
        super("AlignToVisionTarget", kP, kI, kD, period);
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(.1);
        getPIDController().setOutputRange(-1, 1);
        
    }
    @Override
    protected double returnPIDInput() {
        // TODO Auto-generated method stub
        return Robot.utilities.XRSGyro.getAngle();
    }
    @Override
    protected void usePIDOutput(double output) {
        // TODO Auto-generated method stub
        Robot.driveTrain.drive(throttle, output); // Convert output angle to angular power
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
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
        centerX = visionTable.getNumberArray("centerX", new double[] {}); 
        return Math.abs(centerX[0] - 320) < 5;
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