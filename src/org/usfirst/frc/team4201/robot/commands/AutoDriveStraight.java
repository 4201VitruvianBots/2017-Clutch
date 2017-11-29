package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveStraight extends CommandGroup {

    public AutoDriveStraight() {
        addSequential(new DriveStraightWithGyro(5, -1));
    }
}
