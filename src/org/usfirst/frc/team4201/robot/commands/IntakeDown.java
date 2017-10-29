package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeDown extends CommandGroup {

    public IntakeDown() {
    	addSequential(new DeployGroundGearIntake());
    	addSequential(new OpenGearClamps());
    	addSequential(new EnableIntakeMotors());
    }
}
