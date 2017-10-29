package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeUp extends CommandGroup {

    public IntakeUp() {
    	addSequential(new DisableIntakeMotors());
    	addSequential(new CloseGearClamps());
    	addSequential(new RetractGroundGearIntake());
    }
}
